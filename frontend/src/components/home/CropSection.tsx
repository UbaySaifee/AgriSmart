import { Wheat, ArrowRight } from "lucide-react";

export default function CropSection() {
  return (
    <section className="py-24 bg-slate-50">

      <div className="max-w-7xl mx-auto px-6">

        <div className="grid lg:grid-cols-2 gap-16 items-center">

          <div>

            <span className="text-green-600 font-bold uppercase">
              AI Crop Recommendation
            </span>

            <h2 className="text-5xl font-black mt-5">
              Choose The Best Crop
            </h2>

            <p className="text-slate-600 mt-8 leading-8">
              Based on weather, soil quality and location,
              AgriSmart recommends the most profitable crop
              using Artificial Intelligence.
            </p>

            <button className="mt-10 bg-green-600 hover:bg-green-700 text-white px-8 py-4 rounded-2xl flex items-center gap-3 font-bold">
              Try AI Recommendation
              <ArrowRight size={20}/>
            </button>

          </div>

          <div className="bg-white rounded-[35px] shadow-xl p-10">

            <div className="flex items-center gap-5">

              <div className="bg-green-100 p-5 rounded-2xl">
                <Wheat className="text-green-700" size={35}/>
              </div>

              <div>
                <h3 className="text-3xl font-black">
                  Wheat
                </h3>

                <p className="text-slate-500">
                  Best crop for current conditions.
                </p>

              </div>

            </div>

            <div className="mt-10 space-y-5">

              <div className="flex justify-between">
                <span>Soil Quality</span>
                <strong>Excellent</strong>
              </div>

              <div className="flex justify-between">
                <span>Water Need</span>
                <strong>Moderate</strong>
              </div>

              <div className="flex justify-between">
                <span>Profit Prediction</span>
                <strong className="text-green-700">
                  High
                </strong>
              </div>

            </div>

          </div>

        </div>

      </div>

    </section>
  );
}