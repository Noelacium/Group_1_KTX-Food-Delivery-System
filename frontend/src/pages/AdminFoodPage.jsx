import { useEffect, useState } from 'react';
import { api } from '../services/api.js';

const emptyForm = { name: '', price: '', category: '', available: true, quantity: '' };

export default function AdminFoodPage() {
  const [foods, setFoods] = useState([]);
  const [form, setForm] = useState(emptyForm);
  const [editingId, setEditingId] = useState(null);
  const [error, setError] = useState('');

  const loadFoods = () => api.getAllFoods().then(setFoods);
 
  useEffect(() => { loadFoods(); }, []);
 
  const totalFoods = foods.length;
  const availableFoods = foods.filter((food) => food.available).length;
  const lowStockFoods = foods.filter((food) => food.quantity <= 5).length;

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setForm({ ...form, [name]: type === 'checkbox' ? checked : value });
  };

  const resetForm = () => {
    setForm(emptyForm);
    setEditingId(null);
  };

  const handleSubmit = async () => {
    setError('');
    try {
      const payload = {
        ...form,
        price: Number(form.price),
        quantity: Number(form.quantity),
      };
      if (editingId) {
        await api.updateFood(editingId, payload);
      } else {
        await api.createFood(payload);
      }
      resetForm();
      loadFoods();
    } catch (err) {
      setError(err.message);
    }
  };

  const handleEdit = (food) => {
    setForm({
      name: food.name,
      price: food.price,
      category: food.category,
      available: food.available,
      quantity: food.quantity,
    });
    setEditingId(food.id);
  };

  const handleDelete = async (id) => {
    if (!confirm('Xóa món này?')) return;
    await api.deleteFood(id);
    loadFoods();
  };

  const handleQuantityChange = async (id, quantity) => {
    await api.updateFoodQuantity(id, quantity);
    loadFoods();
  };

  const handleQuantityInlineChange = (id, value) => {
    setFoods((prev) =>
        prev.map((f) => (f.id === id ? { ...f, quantity: Number(value) } : f))
    );
};

  return (
    <div className="page">
      <div className="section-header">
        <div>
          <h1>Quản lý món ăn</h1>
          <p>Thêm, chỉnh sửa và quản lý số lượng món ăn dễ dàng từ trang admin.</p>
        </div>
      </div>

      <div className="section-card admin-dashboard">
        <div className="admin-summary-grid">
          <div className="hero-card admin-metric">
            <strong>{totalFoods}</strong>
            <p>Tổng số món ăn</p>
          </div>
          <div className="hero-card admin-metric">
            <strong>{availableFoods}</strong>
            <p>Món còn hàng</p>
          </div>
          <div className="hero-card admin-metric">
            <strong>{lowStockFoods}</strong>
            <p>Món sắp hết</p>
          </div>
        </div>

        <div className="admin-top-grid">
          <div className="admin-form-panel">
            <div className="panel-header">
              <h2>{editingId ? 'Cập nhật món' : 'Thêm món mới'}</h2>
              <p>Điền thông tin chi tiết để cập nhật thực đơn nhanh chóng.</p>
            </div>
            <div className="admin-form">
              <input name="name" placeholder="Tên món" value={form.name} onChange={handleChange} />
              <input name="price" type="number" placeholder="Giá tiền" value={form.price} onChange={handleChange} />
              <input name="category" placeholder="Loại món" value={form.category} onChange={handleChange} />
              <input name="quantity" type="number" placeholder="Số lượng" value={form.quantity} onChange={handleChange} />
              <label className="checkbox-label">
                <input type="checkbox" name="available" checked={form.available} onChange={handleChange} />
                Còn bán
              </label>
              <div className="form-actions">
                <button onClick={handleSubmit}>{editingId ? 'Cập nhật món' : 'Thêm món mới'}</button>
                {editingId && <button type="button" className="secondary-btn" onClick={resetForm}>Hủy sửa</button>}
              </div>
            </div>
            {error && <p className="error-text">{error}</p>}
          </div>

          <div className="admin-side-panel">
            <div className="hero-card admin-status-card">
              <h3>Quản lý thực đơn</h3>
              <p>Kiểm soát tồn kho và cập nhật trạng thái món ngay lập tức.</p>
              <div className="status-list">
                <div>
                  <strong>{availableFoods}</strong>
                  <span>Món có thể bán</span>
                </div>
                <div>
                  <strong>{lowStockFoods}</strong>
                  <span>Món sắp hết</span>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div className="table-responsive">
          <table className="admin-table">
            <thead>
              <tr>
                <th>Tên món</th><th>Giá</th><th>Loại</th><th>SL</th><th>Trạng thái</th><th></th>
              </tr>
            </thead>
            <tbody>
              {foods.map((food) => (
                <tr key={food.id}>
                  <td>{food.name}</td>
                  <td>{food.price.toLocaleString()}đ</td>
                  <td>{food.category}</td>
                  <td>
                    <input
                      type="number"
                      value={food.quantity}
                      style={{ width: 60 }}
                      onBlur={(e) => handleQuantityChange(food.id, Number(e.target.value))}
                      onChange={(e) => handleQuantityInlineChange(food.id, e.target.value)}
                    />
                  </td>
                  <td>
                    <span className={`status-chip ${food.available ? 'available' : 'unavailable'}`}>
                      {food.available ? 'Còn hàng' : 'Hết hàng'}
                    </span>
                  </td>
                  <td className="admin-actions-cell">
                    <button className="secondary-btn" onClick={() => handleEdit(food)}>Sửa</button>
                    <button className="danger-btn" onClick={() => handleDelete(food.id)}>Xóa</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}