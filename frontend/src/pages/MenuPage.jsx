import { useEffect, useState } from 'react';
import { api } from '../services/api.js';
import FoodCard from '../components/FoodCard.jsx';

export default function MenuPage({ cart }) {
  const [foods, setFoods] = useState([]);
  const [keyword, setKeyword] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    api.getAllFoods().then(setFoods).finally(() => setLoading(false));
  }, []);

  const handleSearch = async (e) => {
    const value = e.target.value;
    setKeyword(value);
    if (value.trim() === '') {
      const all = await api.getAllFoods();
      setFoods(all);
    } else {
      const result = await api.searchFoods(value);
      setFoods(result);
    }
  };

  return (
    <div className="page">
      <div className="section-header">
        <div>
          <h1>Thực đơn</h1>
          <p>Chọn món ngon, thêm vào giỏ và đặt hàng nhanh chóng.</p>
        </div>
      </div>

      <div className="section-card">
        <input
          type="text"
          placeholder="Tìm món ăn..."
          value={keyword}
          onChange={handleSearch}
          className="search-input"
        />

        {loading ? (
          <p>Đang tải...</p>
        ) : (
          <div className="food-grid">
            {foods.map((food) => (
              <FoodCard key={food.id} food={food} onAdd={cart.addToCart} />
            ))}
          </div>
        )}
      </div>
    </div>
  );
}