export default function CartItem({ item, onUpdateQuantity, onRemove }) {
  return (
    <div className="cart-item">
      <div className="item-left">
        <span className="item-name">{item.foodName}</span>
        <span className="item-subtotal">{(item.price * item.quantity).toLocaleString()}đ</span>
      </div>

      <div className="item-right">
        <div className="item-controls">
          <button onClick={() => onUpdateQuantity(item.foodId, item.quantity - 1)}>-</button>
          <span>{item.quantity}</span>
          <button onClick={() => onUpdateQuantity(item.foodId, item.quantity + 1)}>+</button>
        </div>
        <button className="remove-btn" onClick={() => onRemove(item.foodId)}>Xóa</button>
      </div>
    </div>
  );
}