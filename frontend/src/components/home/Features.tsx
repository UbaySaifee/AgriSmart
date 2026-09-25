import {
  CloudSun,
  Leaf,
  Bot,
  Sprout,
  BarChart3,
  ShieldCheck,
} from "lucide-react";

const features = [
  {
    icon: CloudSun,
    title: "Weather Forecast",
    desc: "Real-time weather prediction with AI.",
  },
  {
    icon: Leaf,
    title: "Crop Recommendation",
    desc: "Best crop suggestions using soil & climate.",
  },
  {
    icon: Bot,
    title: "AI Assistant",
    desc: "24×7 farming assistant.",
  },
  {
    icon: Sprout,
    title: "Disease Detection",
    desc: "Identify crop diseases instantly.",
  },
  {
    icon: BarChart3,
    title: "Market Prices",
    desc: "Live mandi prices across India.",
  },
  {
    icon: ShieldCheck,
    title: "Government Schemes",
    desc: "Latest subsidy & farming schemes.",
  },
];

export default function Features() {
  return (
    <section
      id="features"
      className="py-24 bg-slate-50"
    >
      <div className="max-w-7xl mx-auto px-6">

        <h2 className="text-5xl font-black text-center">

          Powerful Features

        </h2>

        <p className="text-center text-slate-500 mt-4">

          Everything farmers need in one platform.

        </p>

        <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-8 mt-16">

          {features.map((item) => {

            const Icon = item.icon;

            return (

              <div
                key={item.title}
                className="bg-white rounded-3xl p-8 shadow-lg hover:-translate-y-2 duration-300"
              >

                <div className="bg-green-100 w-16 h-16 rounded-2xl flex items-center justify-center">

                  <Icon className="text-green-700" size={32} />

                </div>

                <h3 className="text-2xl font-bold mt-6">

                  {item.title}

                </h3>

                <p className="text-slate-600 mt-4">

                  {item.desc}

                </p>

              </div>

            );
          })}

        </div>

      </div>

    </section>
  );
}