const faqs = [
  {
    question: "How does AgriSmart recommend crops?",
    answer:
      "Our AI analyzes weather, soil quality, location and historical farming data to recommend the most suitable crop."
  },
  {
    question: "Is weather data updated in real time?",
    answer:
      "Yes. AgriSmart integrates live weather APIs for accurate forecasts."
  },
  {
    question: "Can AgriSmart detect crop diseases?",
    answer:
      "Yes. Farmers can upload crop images and our AI model predicts possible diseases."
  },
  {
    question: "Does AgriSmart provide mandi prices?",
    answer:
      "Yes. Live mandi prices are displayed from integrated APIs."
  }
];

export default function FAQ() {
  return (
    <section className="py-24 bg-white">

      <div className="max-w-5xl mx-auto px-6">

        <div className="text-center">

          <h2 className="text-5xl font-black">

            Frequently Asked Questions

          </h2>

          <p className="mt-6 text-slate-500">

            Everything you need to know.

          </p>

        </div>

        <div className="mt-16 space-y-6">

          {faqs.map((item) => (

            <div
              key={item.question}
              className="rounded-3xl border p-8 shadow-sm hover:shadow-xl duration-300"
            >

              <h3 className="text-xl font-bold">

                {item.question}

              </h3>

              <p className="mt-4 text-slate-600 leading-8">

                {item.answer}

              </p>

            </div>

          ))}

        </div>

      </div>

    </section>
  );
}