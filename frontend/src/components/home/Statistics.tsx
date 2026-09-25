import {
  Users,
  Wheat,
  CloudRain,
  TrendingUp,
} from "lucide-react";

const stats = [
  {
    icon: <Users size={40} />,
    number: "50K+",
    title: "Active Farmers",
  },
  {
    icon: <Wheat size={40} />,
    number: "2M+",
    title: "Acres Monitored",
  },
  {
    icon: <CloudRain size={40} />,
    number: "99%",
    title: "Weather Accuracy",
  },
  {
    icon: <TrendingUp size={40} />,
    number: "35%",
    title: "Higher Crop Yield",
  },
];

export default function Statistics() {
  return (
    <section className="py-24 bg-green-700 text-white">
      <div className="max-w-7xl mx-auto px-6">

        <div className="text-center">

          <h2 className="text-5xl font-black">
            Trusted Across India
          </h2>

          <p className="mt-6 text-green-100 text-lg">
            Helping thousands of farmers grow smarter every day.
          </p>

        </div>

        <div className="grid md:grid-cols-2 lg:grid-cols-4 gap-8 mt-16">

          {stats.map((item) => (

            <div
              key={item.title}
              className="bg-white/10 backdrop-blur-lg rounded-3xl p-10 text-center hover:bg-white/20 duration-300"
            >

              <div className="flex justify-center text-green-300">
                {item.icon}
              </div>

              <h3 className="text-5xl font-black mt-6">
                {item.number}
              </h3>

              <p className="mt-4 text-green-100">
                {item.title}
              </p>

            </div>

          ))}

        </div>

      </div>
    </section>
  );
}