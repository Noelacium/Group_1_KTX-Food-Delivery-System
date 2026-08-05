import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { api } from '../services/api.js';

export default function AuthPage({ setStudent, setAdmin }) {
  const [mode, setMode] = useState('login'); // 'login' | 'register'
  const [form, setForm] = useState({
    studentId: '', password: '', fullName: '', phoneNumber: '', roomAddress: '',
  });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async () => {
    setError('');
    setLoading(true);
    try {
      // Đăng ký luôn là sinh viên (admin không cần đăng ký)
      if (mode === 'register') {
        const student = await api.register(form);
        setStudent(student);
        localStorage.setItem('student', JSON.stringify(student));
        navigate('/menu');
        return;
      }

      // Đăng nhập: mã "admin" -> đăng nhập admin, còn lại -> đăng nhập sinh viên
      if (form.studentId.trim() === 'admin') {
        const admin = await api.adminLogin({ adminId: form.studentId, password: form.password });
        setAdmin(admin);
        localStorage.setItem('admin', JSON.stringify(admin));
        navigate('/admin/foods');
      } else {
        const student = await api.login({ studentId: form.studentId, password: form.password });
        setStudent(student);
        localStorage.setItem('student', JSON.stringify(student));
        navigate('/menu');
      }
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="page">
      <div className="section-header">
        <div>
          <h1>{mode === 'login' ? 'Đăng nhập tài khoản' : 'Tạo tài khoản sinh viên'}</h1>
          <p>Truy cập nhanh thực đơn và đặt món dễ dàng ngay trong khu KTX.</p>
        </div>
      </div>

      <div className="auth-panel">
        <div className="auth-tabs">
          <button
            className={mode === 'login' ? 'active' : ''}
            onClick={() => { setMode('login'); setError(''); }}
          >
            Đăng nhập
          </button>
          <button
            className={mode === 'register' ? 'active' : ''}
            onClick={() => { setMode('register'); setError(''); }}
          >
            Đăng ký
          </button>
        </div>

        <div className="auth-form">
          <input
            name="studentId"
            placeholder="Mã sinh viên (8 chữ số) hoặc tài khoản admin"
            value={form.studentId}
            onChange={handleChange}
          />

          {mode === 'register' && (
            <>
              <input name="fullName" placeholder="Họ và tên" value={form.fullName} onChange={handleChange} />
              <input name="phoneNumber" placeholder="Số điện thoại" value={form.phoneNumber} onChange={handleChange} />
              <input name="roomAddress" placeholder="Địa chỉ phòng KTX" value={form.roomAddress} onChange={handleChange} />
            </>
          )}

          <input
            name="password"
            type="password"
            placeholder="Mật khẩu"
            value={form.password}
            onChange={handleChange}
          />

          <div className="auth-actions">
            <button onClick={handleSubmit} disabled={loading}>
              {loading ? 'Đang xử lý...' : mode === 'login' ? 'Đăng nhập' : 'Đăng ký'}
            </button>
          </div>

          {error && <p className="error-text">{error}</p>}
        </div>
      </div>
    </div>
  );
}