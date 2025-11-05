import html2canvas from "html2canvas-oklch";

export async function exportImage(contentRef, title) {
  if (!contentRef.current) return;
  try {
    const targetW = 1533;
    const targetH = 2700;
    const el = contentRef.current;
    const rect = el.getBoundingClientRect();
    const scale = Math.min(targetW / rect.width, targetH / rect.height);

    const originalStyle = {
      width: el.style.width,
      height: el.style.height,
      background: el.style.background,
      className: el.className
    };

    el.style.width = `${Math.round(rect.width)}px`;
    el.style.height = `${Math.round(rect.height)}px`;
    el.style.background = "rgb(255,255,255)";
    el.className = el.className.replace("rounded-lg", "")

    const canvas = await html2canvas(el, {
      scale,
      useCORS: true,
      allowTaint: true,
      logging: false,
      backgroundColor: null,
    });

    el.style.width = originalStyle.width;
    el.style.height = originalStyle.height;
    el.style.background = originalStyle.background;
    el.className = originalStyle.className;

    const finalCanvas = document.createElement("canvas");
    finalCanvas.width = targetW;
    finalCanvas.height = targetH;
    const ctx = finalCanvas.getContext("2d");

    ctx.fillStyle = "rgb(255,255,255)";
    ctx.fillRect(0, 0, targetW, targetH);

    const dx = Math.round((targetW - canvas.width) / 2);
    const dy = Math.round((targetH - canvas.height) / 2);

    ctx.drawImage(canvas, dx, dy);

    const dataUrl = finalCanvas.toDataURL("image/png");
    const a = document.createElement("a");
    a.href = dataUrl;
    a.download = `${(title || "reading").replace(/\s+/g, "_")}.png`;
    document.body.appendChild(a);
    a.click();
    a.remove();

    return {data: "Image exported"};
  } catch (err) {
    console.error(err);
    return {error: "Failed to export image. See console."}
  }
}
