import { Star } from "lucide-react";

const testimonials = [
  {
    name: "Rajesh Sharma",
    location: "Madhya Pradesh",
    review:
      "AgriSmart helped me increase my wheat production by nearly 30%. The AI recommendations are very accurate.",
  },
  {
    name: "Anita Patel",
    location: "Gujarat",
    review:
      "Weather alerts saved my crops from heavy rainfall. This platform is extremely useful.",
  },
  {
    name: "Mohammed Khan",
    location: "Maharashtra",
    review:
      "Market price prediction helped me sell at the right time and earn better profits.",
  },
];

export default function Testimonials() {
  return (
    <section
      id="testimonials"
      className="py-24 bg-slate-50"
    >
      <div className="max-w-7xl mx-auto px-6">

        <div className="text-center">

          <h2 className="text-5xl font-black">
            Loved By Farmers
          </h2>

          <p className="mt-6 text-slate-500 text-lg">
            Thousands of farmers trust AgriSmart every day.
          </p>

        </div>

        <div className="grid lg:grid-cols-3 gap-8 mt-16">

          {testimonials.map((item) => (

            <div
              key={item.name}
              className="bg-white rounded-3xl shadow-lg p-8 hover:-translate-y-2 hover:shadow-2xl duration-300"
            >

              <div className="flex text-yellow-500 gap-1">

                <Star fill="currentColor" size={18} />
                <Star fill="currentColor" size={18} />
                <Star fill="currentColor" size={18} />
                <Star fill="currentColor" size={18} />
                <Star fill="currentColor" size={18} />

              </div>

              <p className="mt-6 text-slate-600 leading-8">
                "{item.review}"
              </p>

              <div className="mt-8">

                <h4 className="font-bold text-xl">
                  {item.name}
                </h4>

                <p className="text-slate-500">
                  {item.location}
                </p>

              </div>

            </div>

          ))}

        </div>

      </div>
    </section>
  );
}