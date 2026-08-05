import { Navigate } from 'react-router-dom';

// Chặn truy cập nếu chưa đăng nhập đúng vai trò yêu cầu
export default function ProtectedRoute({ isAllowed, children }) {
  if (!isAllowed) {
    return <Navigate to="/" replace />;
  }
  return children;
}