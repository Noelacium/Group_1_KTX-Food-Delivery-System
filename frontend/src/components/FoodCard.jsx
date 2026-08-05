function categoryEmoji(category){
  if(!category) return '🍽️';
  const c = category.toLowerCase();
  if(c.includes('nước') || c.includes('uống')) return '🥤';
  if(c.includes('cơm')) return '🍚';
  if(c.includes('phở')) return '🍜';
  if(c.includes('bún')) return '🍲';
  if(c.includes('tráng miệng') || c.includes('trà')) return '🍰';
  return '🍱';
}

export default function FoodCard({ food, onAdd }) {
  const emoji = categoryEmoji(food.category);
  const price = typeof food.price === 'number' ? food.price.toLocaleString() + 'đ' : food.price;

  return (
    <div className={`food-card ${!food.available ? 'unavailable' : ''}`} aria-hidden={!food.available}>
      <div className="thumb" aria-hidden>
        {food.image ? (
          <img src={food.image} alt={food.name} />
        ) : (
          <div className="thumb-fallback">{emoji}</div>
        )}
      </div>

      <div className="meta">
        <div>
          <div className="title">{food.name}</div>
          <div className="category">{food.category}</div>
        </div>

        <div className="price">{price}</div>
      </div>

      <div className="desc" style={{marginTop:6, color:'#475569', fontSize:13}}>
        {food.quantity !== undefined ? `Còn ${food.quantity} suất` : ''}
      </div>

      <div className="actions">
        {food.available ? (
          <button aria-label={`Thêm ${food.name} vào giỏ`} onClick={() => onAdd(food)}>Thêm vào giỏ</button>
        ) : (
          <span className="out-of-stock">Hết hàng</span>
        )}
      </div>
    </div>
  );
}