const BASE_URL = 'http://localhost:8080/api';

async function handleResponse(response) {
  // parse JSON safely; fall back to text when content-type isn't JSON
  const contentType = response.headers.get('content-type') || '';
  let data = null;
  if (contentType.includes('application/json')) {
    data = await response.json();
  } else {
    const text = await response.text();
    try { data = JSON.parse(text); } catch { data = { message: text }; }
  }

  if (!response.ok) {
    throw new Error((data && (data.error || data.message)) || 'Đã có lỗi xảy ra');
  }
  return data;
}

export const api = {
  // ===== Food =====
  getAllFoods: () =>
    fetch(`${BASE_URL}/foods`).then(handleResponse),

  searchFoods: (keyword) =>
    fetch(`${BASE_URL}/foods/search?name=${encodeURIComponent(keyword)}`).then(handleResponse),

  createFood: (data) =>
    fetch(`${BASE_URL}/foods`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data),
    }).then(handleResponse),

  updateFood: (id, data) =>
    fetch(`${BASE_URL}/foods/${id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data),
    }).then(handleResponse),

  deleteFood: (id) =>
    fetch(`${BASE_URL}/foods/${id}`, { method: 'DELETE' }).then((res) => {
      if (!res.ok) throw new Error('Xóa món thất bại');
    }),

  updateFoodQuantity: (id, quantity) =>
    fetch(`${BASE_URL}/foods/${id}/quantity`, {
      method: 'PATCH',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ quantity }),
    }).then(handleResponse),

  // ===== Student =====
  getStudent: (id) =>
    fetch(`${BASE_URL}/students/${id}`).then(handleResponse),

  // ===== Auth (sinh viên) =====
  // Backend exposes student endpoints; register -> POST /api/students, login -> GET /api/students/{id}
  register: (data) =>
    fetch(`${BASE_URL}/students`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        id: data.studentId,
        fullName: data.fullName,
        phoneNumber: data.phoneNumber,
        roomAddress: data.roomAddress,
        balance: 0
      }),
    }).then(handleResponse),

  login: (data) => {
    const id = typeof data === 'string' ? data : (data.studentId || data.id);
    return fetch(`${BASE_URL}/students/${encodeURIComponent(id)}`).then(handleResponse);
  },

  // ===== Auth (admin) =====
  adminLogin: (data) =>
    fetch(`${BASE_URL}/admin/auth/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data),
    }).then(handleResponse),

  // ===== Order =====
  createOrder: (orderRequest) =>
    fetch(`${BASE_URL}/orders`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(orderRequest),
    }).then(handleResponse),

  getOrder: (orderId) =>
    fetch(`${BASE_URL}/orders/${orderId}`).then(handleResponse),

  payOrder: (orderId, method) =>
    fetch(`${BASE_URL}/orders/${orderId}/pay`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ method }),
    }).then(handleResponse),
};