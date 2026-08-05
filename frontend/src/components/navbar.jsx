import { Link } from 'react-router-dom';

export default function Navbar({ studentId, isAdmin, cartCount, onLogout }) {
  const isLoggedIn = !!studentId || isAdmin;

  return (
    <nav className="navbar">
      <Link to="/menu" className="logo">
        <span>🍱</span>
        KTX Food
      </Link>

      <div className="nav-links">
        {!isAdmin && isLoggedIn && (
          <>
            <Link to="/menu">Thực đơn</Link>
            <Link to="/cart">Giỏ hàng ({cartCount})</Link>
          </>
        )}
        {isAdmin && <Link to="/admin/foods">Quản lý món ăn</Link>}
      </div>

      <div className="nav-actions">
        {studentId && <span className="student-badge">SV: {studentId}</span>}
        {isAdmin && <span className="student-badge">Admin</span>}
        {isLoggedIn && (
          <button onClick={onLogout} className="logout-btn">Đăng xuất</button>
        )}
      </div>
    </nav>
  );
}