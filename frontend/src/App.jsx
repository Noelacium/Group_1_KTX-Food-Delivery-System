import { Routes, Route } from 'react-router-dom';
import { useState } from 'react';
import Navbar from './components/Navbar.jsx';
import ProtectedRoute from './components/ProtectedRoute.jsx';
import AuthPage from './pages/AuthPage.jsx';
import MenuPage from './pages/MenuPage.jsx';
import CartPage from './pages/CartPage.jsx';
import OrderPage from './pages/OrderPage.jsx';
import AdminFoodPage from './pages/AdminFoodPage.jsx';
import OrderSuccessPage from './pages/OrderSuccessPage.jsx';
import { useCart } from './hooks/useCart.js';

function App() {

  // Đọc localStorage ngay lần render đầu tiên
  const [student, setStudent] = useState(() => {
    const saved = localStorage.getItem("student");
    return saved ? JSON.parse(saved) : null;
  });

  const [admin, setAdmin] = useState(() => {
    const saved = localStorage.getItem("admin");
    return saved ? JSON.parse(saved) : null;
  });

  const cart = useCart();

  const handleLogout = () => {
    setStudent(null);
    setAdmin(null);

    localStorage.removeItem("student");
    localStorage.removeItem("admin");

    cart.clearCart();
  };

  return (
    <div>
      <Navbar
        studentId={student?.id}
        isAdmin={!!admin}
        cartCount={cart.items.length}
        onLogout={handleLogout}
      />

      <Routes>

        <Route
          path="/"
          element={
            <AuthPage
              setStudent={setStudent}
              setAdmin={setAdmin}
            />
          }
        />

        <Route
          path="/menu"
          element={
            <ProtectedRoute isAllowed={!!student}>
              <MenuPage cart={cart} />
            </ProtectedRoute>
          }
        />
        <Route
          path="/cart"
          element={
            <ProtectedRoute isAllowed={!!student}>
              <CartPage cart={cart} />
            </ProtectedRoute>
          }
        />
        <Route
          path="/order"
          element={
            <ProtectedRoute isAllowed={!!student}>
              <OrderPage
                cart={cart}
                studentId={student?.id}
              />
            </ProtectedRoute>
          }
        />

        <Route
          path="/order/success"
          element={
            <ProtectedRoute isAllowed={!!student}>
              <OrderSuccessPage />
            </ProtectedRoute>
          }
        />

        <Route
          path="/admin/foods"
          element={
            <ProtectedRoute isAllowed={!!admin}>
              <AdminFoodPage />
            </ProtectedRoute>
          }
        />
      </Routes>
    </div>
  );
}

export default App;