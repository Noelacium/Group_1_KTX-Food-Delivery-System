import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { api } from '../services/api.js';

export default function OrderPage({ cart, studentId }) {
  const [method, setMethod] = useState('WALLET');
  const [order, setOrder] = useState(null);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleCreateOrder = async () => {
    setError('');
    setLoading(true);
    try {
      const orderRequest = {
        studentId,
        items: cart.items.map((item) => ({
          foodId: item.foodId,
          quantity: item.quantity,
        })),
      };
      const newOrder = await api.createOrder(orderRequest);
      setOrder(newOrder);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const getOrderTotal = (orderData) => {
    if (!orderData) return 0;
    if (typeof orderData.totalAmount === 'number') return orderData.totalAmount;
    return orderData.items?.reduce((sum, item) => sum + (item.price || 0) * (item.quantity || 0), 0) || 0;
  };

  const handlePay = async () => {
    setError('');
    setLoading(true);
    try {
      const paidOrder = await api.payOrder(order.orderId, method);
      cart.clearCart();
      navigate('/order/success', { state: { order: paidOrder } });
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  if (!studentId) {
    return (
      <div className="page">
        <div className="section-header">
          <div>
            <h1>Đặt hàng</h1>
            <p>Bạn cần đăng nhập trước khi đặt món.</p>
          </div>
        </div>
        <div className="section-card empty-state">
          <h3>Chưa đăng nhập</h3>
          <p>Vui lòng đăng nhập để tiếp tục đặt món.</p>
          <button onClick={() => navigate('/')}>Về trang chủ</button>
        </div>
      </div>
    );
  }

  return (
    <div className="page">
      <div className="section-header">
        <div>
          <h1>Đặt hàng</h1>
          <p>Kiểm tra lại giỏ hàng và chọn phương thức thanh toán.</p>
        </div>
      </div>

      {!order && (
        <div className="section-card order-summary-card">
          <div className="order-summary-detail">
            <span>Đơn hàng sẽ có</span>
            <strong>{cart.items.length} món</strong>
          </div>
          <div className="order-summary-detail">
            <span>Tổng dự kiến</span>
            <strong>{cart.totalAmount.toLocaleString()}đ</strong>
          </div>
          <button onClick={handleCreateOrder} disabled={loading || cart.items.length === 0}>
            {loading ? 'Đang xử lý...' : 'Xác nhận đặt hàng'}
          </button>
        </div>
      )}
 
      {order && order.status === 'PENDING' && (
        <div className="payment-box">
          <div className="section-header">
            <div>
              <h2>Thanh toán đơn {order.orderId}</h2>
              <p>Vui lòng chọn phương thức thanh toán để hoàn tất đơn hàng.</p>
            </div>
            <div className="status-badge">Chờ thanh toán</div>
          </div>
 
          <div className="summary-card">
            <p>Giá trị đơn hàng</p>
            <strong style={{ fontSize: '1.4rem' }}>{getOrderTotal(order).toLocaleString()}đ</strong>
          </div>
 
          <select value={method} onChange={(e) => setMethod(e.target.value)}>
            <option value="WALLET">Ví sinh viên</option>
            <option value="CASH">Tiền mặt</option>
            <option value="BANK_TRANSFER">Chuyển khoản</option>
          </select>
          <button onClick={handlePay} disabled={loading}>
            {loading ? 'Đang xử lý...' : 'Thanh toán'}
          </button>
        </div>
      )}

      {order && order.status === 'PAID' && (
        <div className="success-box">
          <h2>✅ Thanh toán thành công</h2>
          <p>Đơn hàng {order.orderId} đã được xác nhận. Cảm ơn bạn đã đặt món!</p>
          <button onClick={() => navigate('/menu')}>Tiếp tục mua sắm</button>
        </div>
      )}

      {error && <p className="error-text">{error}</p>}
    </div>
  );
}