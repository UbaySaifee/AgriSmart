import {
  CloudSun,
  Droplets,
  Wind,
  Thermometer,
} from "lucide-react";

export default function WeatherWidget() {
  return (
    <div className="bg-white rounded-3xl shadow-lg p-8">

      <div className="flex justify-between items-center">

        <div>

          <h2 className="text-2xl font-black">
            Today's Weather
          </h2>

          <p className="text-slate-500 mt-2">
            Indore, Madhya Pradesh
          </p>

        </div>

        <CloudSun
          size={60}
          className="text-yellow-500"
        />

      </div>

      <h1 className="text-6xl font-black mt-8">
        28°C
      </h1>

      <div className="grid grid-cols-3 gap-5 mt-10">

        <div className="bg-slate-100 rounded-2xl p-5 text-center">

          <Droplets
            className="mx-auto text-blue-500"
            size={28}
          />

          <p className="mt-3 text-slate-500">
            Humidity
          </p>

          <h3 className="font-black text-xl">
            62%
          </h3>

        </div>

        <div className="bg-slate-100 rounded-2xl p-5 text-center">

          <Wind
            className="mx-auto text-green-600"
            size={28}
          />

          <p className="mt-3 text-slate-500">
            Wind
          </p>

          <h3 className="font-black text-xl">
            12 km/h
          </h3>

        </div>

        <div className="bg-slate-100 rounded-2xl p-5 text-center">

          <Thermometer
            className="mx-auto text-red-500"
            size={28}
          />

          <p className="mt-3 text-slate-500">
            Feels Like
          </p>

          <h3 className="font-black text-xl">
            30°C
          </h3>

        </div>

      </div>

    </div>
  );
}