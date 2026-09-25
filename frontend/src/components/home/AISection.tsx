import {
  Bot,
  Sparkles,
  ArrowRight,
  BrainCircuit,
  ShieldCheck,
  TrendingUp,
} from "lucide-react";

export default function AISection() {
  return (
    <section
      id="ai"
      className="py-24 bg-gradient-to-br from-emerald-700 via-green-700 to-green-600"
    >
      <div className="max-w-7xl mx-auto px-6">

        <div className="grid lg:grid-cols-2 gap-20 items-center">

          <div>

            <div className="inline-flex items-center gap-3 rounded-full bg-white/20 px-5 py-2 text-white font-semibold">

              <Sparkles size={18} />

              AI Powered Agriculture

            </div>

            <h2 className="mt-8 text-5xl md:text-6xl font-black text-white leading-tight">

              Smart Farming
              <br />
              With Artificial Intelligence

            </h2>

            <p className="mt-8 text-lg leading-8 text-green-100">

              AgriSmart uses Artificial Intelligence to recommend
              crops, predict diseases, analyze soil health,
              estimate production and improve farmers'
              profitability.

            </p>

            <div className="flex flex-wrap gap-5 mt-10">

              <button className="bg-white text-green-700 px-8 py-4 rounded-2xl font-bold flex items-center gap-3 hover:scale-105 duration-300">

                Try AI

                <ArrowRight size={20} />

              </button>

              <button className="border border-white text-white px-8 py-4 rounded-2xl font-bold hover:bg-white hover:text-green-700 duration-300">

                Learn More

              </button>

            </div>

          </div>

          <div>

            <div className="bg-white rounded-[35px] shadow-2xl p-10">

              <div className="w-20 h-20 rounded-full bg-green-600 flex items-center justify-center text-white">

                <Bot size={42} />

              </div>

              <h3 className="text-3xl font-black mt-8">

                AI Recommendation

              </h3>

              <p className="text-slate-500 mt-4">

                Based on today's weather and soil condition.

              </p>

              <div className="space-y-5 mt-10">

                <div className="flex gap-4 bg-green-50 p-5 rounded-2xl">

                  <BrainCircuit className="text-green-600" />

                  <div>

                    <h4 className="font-bold">

                      Recommended Crop

                    </h4>

                    <p className="text-slate-500">

                      Wheat • Confidence 96%

                    </p>

                  </div>

                </div>

                <div className="flex gap-4 bg-green-50 p-5 rounded-2xl">

                  <TrendingUp className="text-green-600" />

                  <div>

                    <h4 className="font-bold">

                      Expected Profit

                    </h4>

                    <p className="text-slate-500">

                      ₹82,000 per acre

                    </p>

                  </div>

                </div>

                <div className="flex gap-4 bg-green-50 p-5 rounded-2xl">

                  <ShieldCheck className="text-green-600" />

                  <div>

                    <h4 className="font-bold">

                      Disease Risk

                    </h4>

                    <p className="text-slate-500">

                      Low Risk (8%)

                    </p>

                  </div>

                </div>

              </div>

            </div>

          </div>

        </div>

      </div>
    </section>
  );
}