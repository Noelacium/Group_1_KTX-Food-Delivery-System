import { useLocation, useNavigate } from 'react-router-dom';

const getOrderTotal = (order) => {
  if (!order) return 0;
  if (typeof order.totalAmount === 'number') return order.totalAmount;
  return order.items?.reduce((sum, item) => sum + (item.price || 0) * (item.quantity || 0), 0) || 0;
};

export default function OrderSuccessPage() {
  const navigate = useNavigate();
  const location = useLocation();
  const order = location.state?.order;

  if (!order) {
    return (
      <div className="page">
        <div className="section-header">
          <div>
            <h1>Đơn hàng đã hoàn tất</h1>
            <p>Không tìm thấy chi tiết đơn hàng. Quay lại thực đơn để tiếp tục.</p>
          </div>
        </div>
        <div className="section-card empty-state">
          <button onClick={() => navigate('/menu')}>Về thực đơn</button>
        </div>
      </div>
    );
  }

  return (
    <div className="page">
      <div className="section-header">
        <div>
          <h1>Đặt hàng thành công</h1>
          <p>Đơn hàng của bạn đã được ghi nhận và đang được xử lý.</p>
        </div>
      </div>

      <div className="section-card success-box">
        <h2>🎉 Thanh toán thành công!</h2>
        <p>Đơn hàng <strong>{order.orderId}</strong> đã được xác nhận.</p>
        <div className="order-summary-detail">
          <span>Trạng thái đơn</span>
          <strong>{order.status}</strong>
        </div>
        <div className="order-summary-detail">
          <span>Tổng giá trị</span>
          <strong>{getOrderTotal(order).toLocaleString()}đ</strong>
        </div>
        <div className="order-items-table">
          <h3>Chi tiết đơn hàng</h3>
          <ul>
            {order.items?.map((item) => (
              <li key={item.foodId}>
                <span>{item.foodName} × {item.quantity}</span>
                <strong>{(item.price * item.quantity).toLocaleString()}đ</strong>
              </li>
            ))}
          </ul>
        </div>
        <div className="order-actions-group">
          <button onClick={() => navigate('/menu')}>Tiếp tục mua sắm</button>
          <button type="button" className="secondary-btn" onClick={() => navigate('/cart')}>Xem giỏ hàng</button>
        </div>
      </div>
    </div>
  );
}
