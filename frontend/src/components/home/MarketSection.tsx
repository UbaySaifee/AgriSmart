import { TrendingUp } from "lucide-react";

const crops = [
  { name: "Wheat", price: "₹2450 / Quintal", change: "+2.3%" },
  { name: "Rice", price: "₹2150 / Quintal", change: "+1.2%" },
  { name: "Soybean", price: "₹4680 / Quintal", change: "-0.8%" },
  { name: "Cotton", price: "₹7120 / Quintal", change: "+3.5%" },
];

export default function MarketSection() {
  return (
    <section id="market" className="py-24 bg-white">
      <div className="max-w-7xl mx-auto px-6">

        <div className="text-center">
          <span className="text-green-600 font-bold uppercase">
            Live Mandi Prices
          </span>

          <h2 className="text-5xl font-black mt-4">
            Today's Crop Market
          </h2>
        </div>

        <div className="mt-16 bg-white rounded-3xl shadow-xl overflow-hidden">

          <table className="w-full">

            <thead className="bg-green-600 text-white">

              <tr>

                <th className="p-5 text-left">Crop</th>

                <th className="p-5 text-left">Price</th>

                <th className="p-5 text-left">Change</th>

              </tr>

            </thead>

            <tbody>

              {crops.map((crop) => (

                <tr
                  key={crop.name}
                  className="border-b hover:bg-green-50"
                >

                  <td className="p-5 font-semibold">

                    {crop.name}

                  </td>

                  <td className="p-5">

                    {crop.price}

                  </td>

                  <td className="p-5 flex items-center gap-2 text-green-600 font-bold">

                    <TrendingUp size={18} />

                    {crop.change}

                  </td>

                </tr>

              ))}

            </tbody>

          </table>

        </div>

      </div>
    </section>
  );
}