import {
  ArrowRight,
  Leaf,
  CloudSun,
  BarChart3,
  ShieldCheck,
} from "lucide-react";

export default function Hero() {
  return (
    <section className="relative overflow-hidden bg-gradient-to-br from-green-700 via-green-600 to-emerald-500">

      <div className="absolute w-96 h-96 rounded-full bg-green-300/20 blur-3xl -top-32 -left-32"/>

      <div className="absolute w-96 h-96 rounded-full bg-emerald-200/20 blur-3xl bottom-0 right-0"/>

      <div className="max-w-7xl mx-auto px-6 py-24">

        <div className="grid lg:grid-cols-2 gap-20 items-center">

          <div>

            <div className="inline-flex items-center gap-3 px-5 py-2 rounded-full bg-white/20 text-white font-semibold">

              <Leaf size={18}/>

              AI Powered Smart Agriculture

            </div>

            <h1 className="text-6xl lg:text-7xl font-black text-white mt-8 leading-tight">

              Grow

              <br/>

              Smarter,

              <br/>

              Harvest Better.

            </h1>

            <p className="text-green-100 text-xl mt-8 leading-9">

              AgriSmart helps farmers with AI based crop
              recommendations, weather forecasting,
              disease detection and live mandi prices.

            </p>

            <div className="flex flex-wrap gap-5 mt-10">

              <button className="bg-white text-green-700 px-8 py-4 rounded-2xl font-bold flex items-center gap-3 hover:scale-105 duration-300">

                Get Started

                <ArrowRight size={20}/>

              </button>

              <button className="border-2 border-white text-white px-8 py-4 rounded-2xl font-bold hover:bg-white hover:text-green-700 duration-300">

                Watch Demo

              </button>

            </div>

          </div>

          <div>

            <div className="bg-white rounded-[35px] shadow-2xl p-8">

              <h2 className="text-3xl font-black mb-8">

                Today's Farm Overview

              </h2>

              <div className="space-y-6">

                <div className="flex justify-between items-center bg-green-50 rounded-xl p-5">

                  <div className="flex items-center gap-4">

                    <CloudSun className="text-green-600"/>

                    Weather

                  </div>

                  <strong>28°C Sunny</strong>

                </div>

                <div className="flex justify-between items-center bg-green-50 rounded-xl p-5">

                  <div className="flex items-center gap-4">

                    <BarChart3 className="text-green-600"/>

                    Wheat Price

                  </div>

                  <strong>₹2450/Qtl</strong>

                </div>

                <div className="flex justify-between items-center bg-green-50 rounded-xl p-5">

                  <div className="flex items-center gap-4">

                    <ShieldCheck className="text-green-600"/>

                    Crop Health

                  </div>

                  <strong className="text-green-700">

                    Excellent

                  </strong>

                </div>

              </div>

            </div>

          </div>

        </div>

      </div>

    </section>
  );
}