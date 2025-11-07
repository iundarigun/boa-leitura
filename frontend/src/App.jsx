import {Route, Routes} from "react-router-dom";

import Layout from "@/components/Layout";
import AuthorsListPage from "@/features/authors/pages/AuthorsListPage";
import AuthorFormPage from "@/features/authors/pages/AuthorFormPage";
import GenresListPage from "@/features/genres/pages/GenresListPage";
import GenreFormPage from "@/features/genres/pages/GenreFormPage";
import SagasListPage from "@/features/sagas/pages/SagasListPage";
import SagaFormPage from "@/features/sagas/pages/SagaFormPage";
import BooksListPage from "@/features/books/pages/BookListPage";
import BookFormPage from "@/features/books/pages/BookFormPage";
import ReadingsListPage from "@/features/reading/pages/ReadingsListPage";
import ReadingFormPage from "@/features/reading/pages/ReadingFormPage";
import StatisticsSummaryPage from "@/features/statistics/pages/StatisticsSummaryPage";
import StatisticsLanguagePage from "@/features/statistics/pages/StatisticsLanguagePage";
import StatisticsAuthorPage from "@/features/statistics/pages/StatisticsAuthorPage.jsx";
import StatisticsMoodPage from "@/features/statistics/pages/StatisticsMoodPage.jsx";
import LoginPage from "@/features/auth/pages/LoginPage.jsx";
import PrivateRoute from "@/components/PrivateRoute.jsx";
import ToBeReadsListPage from "@/features/tbr/pages/ToBeReadsListPage.jsx";
import ToBeReadFormPage from "@/features/tbr/pages/ToBeReadFormPage.jsx";
import RegisterPage from "@/features/auth/pages/RegisterPage.jsx";
import ImportGoodreadsPage from "@/features/user/pages/ImportGoodreadsPage.jsx";
import UserPreferencesPage from "@/features/user/pages/UserPreferencesPage.jsx";

function App() {
  return (
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route path="/authors" element={<PrivateRoute><AuthorsListPage /></PrivateRoute>} />
          <Route path="/authors/new" element={<PrivateRoute><AuthorFormPage /></PrivateRoute>} />
          <Route path="/authors/:id/edit" element={<PrivateRoute><AuthorFormPage /></PrivateRoute>} />
          <Route path="/genres" element={<PrivateRoute><GenresListPage /></PrivateRoute>} />
          <Route path="/genres/new" element={<PrivateRoute><GenreFormPage /></PrivateRoute>} />
          <Route path="/genres/:id/edit" element={<PrivateRoute><GenreFormPage /></PrivateRoute>} />
          <Route path="/sagas" element={<PrivateRoute><SagasListPage /></PrivateRoute>} />
          <Route path="/sagas/new" element={<PrivateRoute><SagaFormPage /></PrivateRoute>} />
          <Route path="/sagas/:id/edit" element={<PrivateRoute><SagaFormPage /></PrivateRoute>} />
          <Route path="/books" element={<PrivateRoute><BooksListPage /></PrivateRoute>} />
          <Route path="/books/new" element={<PrivateRoute><BookFormPage /></PrivateRoute>} />
          <Route path="/books/:id/edit" element={<PrivateRoute><BookFormPage /></PrivateRoute>} />
          <Route path="/readings" element={<PrivateRoute><ReadingsListPage /></PrivateRoute>} />
          <Route path="/readings/:id/edit" element={<PrivateRoute><ReadingFormPage /></PrivateRoute>} />
          <Route path="/readings/new/:bookId" element={<PrivateRoute><ReadingFormPage /></PrivateRoute>} />
          <Route path="/tbr" element={<PrivateRoute><ToBeReadsListPage /></PrivateRoute>} />
          <Route path="/tbr/:id/edit" element={<PrivateRoute><ToBeReadFormPage /></PrivateRoute>} />
          <Route path="/statistics/summary" element={<PrivateRoute><StatisticsSummaryPage /></PrivateRoute>} />
          <Route path="/statistics/language" element={<PrivateRoute><StatisticsLanguagePage /></PrivateRoute>} />
          <Route path="/statistics/author" element={<PrivateRoute><StatisticsAuthorPage /></PrivateRoute>} />
          <Route path="/statistics/mood" element={<PrivateRoute><StatisticsMoodPage /></PrivateRoute>} />
          <Route path="/user/import" element={<PrivateRoute><ImportGoodreadsPage/></PrivateRoute>} />
          <Route path="/user/preferences" element={<PrivateRoute><UserPreferencesPage/></PrivateRoute>} />
          <Route path="/login" element={<LoginPage />}/>
          <Route path="/register" element={<RegisterPage />}/>
        </Route>
      </Routes>
  );
}

export default App;
