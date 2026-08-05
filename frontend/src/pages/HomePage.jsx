import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { api } from '../services/api.js';

export default function HomePage({ studentId, setStudentId }) {
  const [input, setInput] = useState(studentId);
  const [student, setStudent] = useState(null);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleLogin = async () => {
    setError('');
    try {
      const data = await api.getStudent(input.trim());
      setStudent(data);
      setStudentId(data.id);
    } catch (err) {
      setError(err.message);
      setStudent(null);
    }
  };

  return (
    <div className="page">
      <h1>Đăng nhập sinh viên</h1>
      <div className="login-box">
        <input
          type="text"
          placeholder="Nhập mã sinh viên (VD: SV-00000001)"
          value={input}
          onChange={(e) => setInput(e.target.value)}
        />
        <button onClick={handleLogin}>Đăng nhập</button>
      </div>

      {error && <p className="error-text">{error}</p>}

      {student && (
        <div className="student-info">
          <p><strong>{student.fullName}</strong></p>
          <p>Phòng: {student.roomAddress}</p>
          <p>Số dư: {student.balance.toLocaleString()}đ</p>
          <button onClick={() => navigate('/menu')}>Xem thực đơn →</button>
        </div>
      )}
    </div>
  );
}