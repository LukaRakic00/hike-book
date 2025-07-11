"use client";
import { useAuth } from "../../hooks/useAuth";

export default function DashboardPage() {
  const { user, signOut, isLoading } = useAuth();

  const handleLogout = async () => {
    await signOut();
    window.location.href = "/auth";
  };

  return (
    <div style={{ minHeight: "100vh", display: "flex", flexDirection: "column", justifyContent: "center", alignItems: "center", background: "#f5f7fa" }}>
      <div style={{ background: "white", padding: 32, borderRadius: 16, boxShadow: "0 2px 16px #0001", minWidth: 320, textAlign: "center" }}>
        <h1 style={{ marginBottom: 16 }}>Dobrodošao{user?.name ? `, ${user.name}` : "!"}</h1>
        <p style={{ marginBottom: 32 }}>Uspešno si prijavljen na Hike&Book.</p>
        <button onClick={handleLogout} disabled={isLoading} style={{ padding: "10px 32px", borderRadius: 8, background: "#111827", color: "white", border: "none", fontWeight: 600, fontSize: 16, cursor: "pointer" }}>
          {isLoading ? "Odjavljujem..." : "Odjavi se"}
        </button>
      </div>
    </div>
  );
} 