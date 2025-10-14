-- jobrunr_backgroundjobservers
create table jobrunr_backgroundjobservers
(
    id                         char(36)      not null primary key,
    workerpoolsize             integer       not null,
    pollintervalinseconds      integer       not null,
    firstheartbeat             timestamp(6)  not null,
    lastheartbeat              timestamp(6)  not null,
    running                    integer       not null,
    systemtotalmemory          bigint        not null,
    systemfreememory           bigint        not null,
    systemcpuload              numeric(3, 2) not null,
    processmaxmemory           bigint        not null,
    processfreememory          bigint        not null,
    processallocatedmemory     bigint        not null,
    processcpuload             numeric(3, 2) not null,
    deletesucceededjobsafter   varchar(32),
    permanentlydeletejobsafter varchar(32),
    name                       varchar(128)
);

create index jobrunr_bgjobsrvrs_fsthb_idx
    on jobrunr_backgroundjobservers (firstheartbeat);

create index jobrunr_bgjobsrvrs_lsthb_idx
    on jobrunr_backgroundjobservers (lastheartbeat);

-- jobrunr_jobs
create table jobrunr_jobs
(
    id             char(36)     not null
        primary key,
    version        integer      not null,
    jobasjson      text         not null,
    jobsignature   varchar(512) not null,
    state          varchar(36)  not null,
    createdat      timestamp    not null,
    updatedat      timestamp    not null,
    scheduledat    timestamp,
    recurringjobid varchar(128)
);

create index jobrunr_state_idx
    on jobrunr_jobs (state);

create index jobrunr_job_signature_idx
    on jobrunr_jobs (jobsignature);

create index jobrunr_job_created_at_idx
    on jobrunr_jobs (createdat);

create index jobrunr_job_scheduled_at_idx
    on jobrunr_jobs (scheduledat);

create index jobrunr_job_rci_idx
    on jobrunr_jobs (recurringjobid);

create index jobrunr_jobs_state_updated_idx
    on jobrunr_jobs (state, updatedat);

-- jobrunr_metadata
create table jobrunr_metadata
(
    id        varchar(156) not null
    primary key,
    name      varchar(92)  not null,
    owner     varchar(64)  not null,
    value     text         not null,
    createdat timestamp    not null,
    updatedat timestamp    not null
);

-- jobrunr_migrations
create table jobrunr_migrations
(
    id          char(36)    not null
        primary key,
    script      varchar(64) not null,
    installedon varchar(29) not null
);

-- jobrunr_recurring_jobs
create table jobrunr_recurring_jobs
(
    id        char(128)                  not null
        primary key,
    version   integer                    not null,
    jobasjson text                       not null,
    createdat bigint default '0'::bigint not null
);

create index jobrunr_recurring_job_created_at_idx
    on jobrunr_recurring_jobs (createdat);


-- view job_stat_results
create view jobrunr_jobs_stats
            (total, awaiting, scheduled, enqueued, processing, processed, failed, succeeded, alltimesucceeded, deleted,
             nbrofbackgroundjobservers, nbrofrecurringjobs)
as
WITH job_stat_results AS (SELECT jobrunr_jobs.state,
                                 count(*) AS count
                          FROM jobrunr_jobs
                          GROUP BY jobrunr_jobs.state)
SELECT COALESCE((SELECT sum(job_stat_results.count) AS sum
                 FROM job_stat_results), 0::numeric)                                      AS total,
       COALESCE((SELECT sum(job_stat_results.count) AS sum
                 FROM job_stat_results
                 WHERE job_stat_results.state::text = 'AWAITING'::text), 0::numeric)      AS awaiting,
       COALESCE((SELECT sum(job_stat_results.count) AS sum
                 FROM job_stat_results
                 WHERE job_stat_results.state::text = 'SCHEDULED'::text), 0::numeric)     AS scheduled,
       COALESCE((SELECT sum(job_stat_results.count) AS sum
                 FROM job_stat_results
                 WHERE job_stat_results.state::text = 'ENQUEUED'::text), 0::numeric)      AS enqueued,
       COALESCE((SELECT sum(job_stat_results.count) AS sum
                 FROM job_stat_results
                 WHERE job_stat_results.state::text = 'PROCESSING'::text), 0::numeric)    AS processing,
       COALESCE((SELECT sum(job_stat_results.count) AS sum
                 FROM job_stat_results
                 WHERE job_stat_results.state::text = 'PROCESSED'::text), 0::numeric)     AS processed,
       COALESCE((SELECT sum(job_stat_results.count) AS sum
                 FROM job_stat_results
                 WHERE job_stat_results.state::text = 'FAILED'::text), 0::numeric)        AS failed,
       COALESCE((SELECT sum(job_stat_results.count) AS sum
                 FROM job_stat_results
                 WHERE job_stat_results.state::text = 'SUCCEEDED'::text), 0::numeric)     AS succeeded,
       COALESCE((SELECT jm.value::character(10)::numeric(10, 0) AS value
                 FROM jobrunr_metadata jm
                 WHERE jm.id::text = 'succeeded-jobs-counter-cluster'::text), 0::numeric) AS alltimesucceeded,
       COALESCE((SELECT sum(job_stat_results.count) AS sum
                 FROM job_stat_results
                 WHERE job_stat_results.state::text = 'DELETED'::text), 0::numeric)       AS deleted,
       (SELECT count(*) AS count
        FROM jobrunr_backgroundjobservers)                                                AS nbrofbackgroundjobservers,
       (SELECT count(*) AS count
        FROM jobrunr_recurring_jobs)                                                      AS nbrofrecurringjobs;
