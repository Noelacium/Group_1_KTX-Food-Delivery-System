import { useNavigate } from 'react-router-dom';
import CartItem from '../components/CartItem.jsx';

export default function CartPage({ cart }) {
  const navigate = useNavigate();

  if (cart.items.length === 0) {
    return (
      <div className="page">
        <div className="section-header">
          <div>
            <h1>Giỏ hàng</h1>
            <p>Hiện tại chưa có món nào trong giỏ hàng của bạn.</p>
          </div>
        </div>
        <div className="empty-state section-card">
          <h3>Giỏ hàng trống</h3>
          <p>Hãy quay lại thực đơn và thêm món bạn thích để bắt đầu đặt hàng.</p>
          <button onClick={() => navigate('/menu')}>Xem thực đơn</button>
        </div>
      </div>
    );
  }

  return (
    <div className="page">
      <div className="section-header">
        <div>
          <h1>Giỏ hàng</h1>
          <p>Kiểm tra lại đơn hàng trước khi chuyển sang bước thanh toán.</p>
        </div>
      </div>

      <div className="section-card">
        {cart.items.map((item) => (
          <CartItem
            key={item.foodId}
            item={item}
            onUpdateQuantity={cart.updateQuantity}
            onRemove={cart.removeFromCart}
          />
        ))}

        <div className="cart-actions">
          <div className="cart-total">
            <strong>Tổng cộng: {cart.totalAmount.toLocaleString()}đ</strong>
          </div>
          <button onClick={() => navigate('/order')}>Tiến hành đặt hàng →</button>
        </div>
      </div>
    </div>
  );
}