import { useState } from 'react';

export function useCart() {
  const [items, setItems] = useState([]); // [{ foodId, foodName, price, quantity }]

  const addToCart = (food, quantity = 1) => {
    setItems((prev) => {
      const existing = prev.find((item) => item.foodId === food.id);
      if (existing) {
        return prev.map((item) =>
          item.foodId === food.id
            ? { ...item, quantity: item.quantity + quantity }
            : item
        );
      }
      return [
        ...prev,
        { foodId: food.id, foodName: food.name, price: food.price, quantity },
      ];
    });
  };

  const removeFromCart = (foodId) => {
    setItems((prev) => prev.filter((item) => item.foodId !== foodId));
  };

  const updateQuantity = (foodId, quantity) => {
    if (quantity < 1) return;
    setItems((prev) =>
      prev.map((item) => (item.foodId === foodId ? { ...item, quantity } : item))
    );
  };

  const clearCart = () => setItems([]);

  const totalAmount = items.reduce((sum, item) => sum + item.price * item.quantity, 0);

  return { items, addToCart, removeFromCart, updateQuantity, clearCart, totalAmount };
}