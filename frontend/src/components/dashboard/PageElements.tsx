import type { ReactNode } from "react";
import { LoaderCircle, RefreshCw } from "lucide-react";

export function PageHeading({ eyebrow, title, description, action }: { eyebrow: string; title: string; description: string; action?: ReactNode }) {
  return <div className="workspace-heading"><div><p className="eyebrow">{eyebrow}</p><h1>{title}</h1><p>{description}</p></div>{action}</div>;
}

export function LoadingState({ label = "Loading your farm data…" }: { label?: string }) {
  return <div className="state-card compact-state"><LoaderCircle className="spin" size={25} /><p>{label}</p></div>;
}

export function ErrorState({ message, retry }: { message: string; retry?: () => void }) {
  return <div className="state-card compact-state error-state"><h2>Something needs attention</h2><p>{message}</p>{retry && <button className="secondary-button" onClick={retry}><RefreshCw size={16} /> Try again</button>}</div>;
}

export function Field({ label, children }: { label: string; children: ReactNode }) {
  return <label className="field-label"><span>{label}</span>{children}</label>;
}
