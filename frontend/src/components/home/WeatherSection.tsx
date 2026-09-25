import { CloudSun, Wind, Droplets, Thermometer } from "lucide-react";

export default function WeatherSection() {
  return (
    <section id="weather" className="py-24 bg-white">
      <div className="max-w-7xl mx-auto px-6">

        <div className="text-center">
          <span className="text-green-600 font-bold uppercase tracking-widest">
            Live Weather
          </span>

          <h2 className="text-5xl font-black mt-4">
            Smart Weather Forecast
          </h2>

          <p className="mt-5 text-slate-600 max-w-2xl mx-auto">
            Stay ahead with accurate weather predictions and protect your crops
            using AI-driven forecasting.
          </p>
        </div>

        <div className="grid md:grid-cols-2 lg:grid-cols-4 gap-8 mt-16">

          <div className="bg-green-50 rounded-3xl p-8 shadow-lg">
            <CloudSun className="text-green-600" size={40}/>
            <h3 className="text-2xl font-bold mt-5">Sunny</h3>
            <p className="mt-2 text-slate-600">28°C</p>
          </div>

          <div className="bg-green-50 rounded-3xl p-8 shadow-lg">
            <Wind className="text-green-600" size={40}/>
            <h3 className="text-2xl font-bold mt-5">Wind</h3>
            <p className="mt-2 text-slate-600">12 km/h</p>
          </div>

          <div className="bg-green-50 rounded-3xl p-8 shadow-lg">
            <Droplets className="text-green-600" size={40}/>
            <h3 className="text-2xl font-bold mt-5">Humidity</h3>
            <p className="mt-2 text-slate-600">68%</p>
          </div>

          <div className="bg-green-50 rounded-3xl p-8 shadow-lg">
            <Thermometer className="text-green-600" size={40}/>
            <h3 className="text-2xl font-bold mt-5">Feels Like</h3>
            <p className="mt-2 text-slate-600">30°C</p>
          </div>

        </div>
      </div>
    </section>
  );
}