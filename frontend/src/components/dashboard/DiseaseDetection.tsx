import { Upload, Image, ScanSearch } from "lucide-react";

export default function DiseaseDetection() {
  return (
    <div className="bg-white rounded-3xl shadow-lg p-8">

      <div className="flex items-center gap-3">

        <ScanSearch
          size={36}
          className="text-green-600"
        />

        <div>

          <h2 className="text-3xl font-black">

            AI Disease Detection

          </h2>

          <p className="text-slate-500 mt-1">

            Upload a crop leaf image to detect diseases.

          </p>

        </div>

      </div>

      <div className="mt-10 border-2 border-dashed border-green-400 rounded-3xl p-14 flex flex-col items-center justify-center">

        <Image
          size={80}
          className="text-green-600"
        />

        <h3 className="text-2xl font-bold mt-6">

          Upload Crop Image

        </h3>

        <p className="text-slate-500 mt-3 text-center">

          Supported formats:
          JPG, PNG, JPEG

        </p>

        <input
          type="file"
          className="hidden"
          id="crop-image"
        />

        <label
          htmlFor="crop-image"
          className="mt-8 cursor-pointer bg-green-600 hover:bg-green-700 text-white px-8 py-4 rounded-2xl flex items-center gap-3 transition"
        >

          <Upload size={22} />

          Choose Image

        </label>

      </div>

      <button
        className="w-full mt-8 bg-emerald-600 hover:bg-emerald-700 text-white py-4 rounded-2xl font-bold transition"
      >

        Analyze Disease

      </button>

    </div>
  );
}