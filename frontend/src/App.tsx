import { useState, useEffect } from "react";
import { type Product, type ProductRequest, type Category } from "./types";
import { getAllProducts, searchProducts, createProduct, updateProduct, deleteProduct } from "./api";
import SearchBar from "./components/SearchBar";
import ProductForm from "./components/ProductForm";
import ProductList from "./components/ProductList";
import "./App.css";
import axios from "axios";

const App = () => {
  const [products, setProducts] = useState<Product[]>([]);
  const [showForm, setShowForm] = useState(false);
  const [editProduct, setEditProduct] = useState<Product | null>(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const reload = () => {
    setLoading(true);
    setError(null);
    getAllProducts()
        .then(res => setProducts(res.data))
        .catch(() => setError("Failed to load products"))
        .finally(() => setLoading(false));
  };

  useEffect(() => {
    reload();
  }, []);

  const handleSearch = async (search: string, category: Category | undefined, available: boolean | undefined) => {
    setLoading(true);
    setError(null);
    try {
      const res = await searchProducts(search, category, available);
      setProducts(res.data);
      setShowForm(false);
      setEditProduct(null);
    } catch {
      setError("Search failed");
    } finally {
      setLoading(false);
    }
  };

  const handleCreate = async (product: ProductRequest) => {
    try {
      await createProduct(product);
      setShowForm(false);
      reload();
    } catch (error: unknown) {
      if (axios.isAxiosError(error) && error.response) {
        alert(error.response.data.message);
      }
    }
  };

  const handleUpdate = async (product: ProductRequest) => {
    if (editProduct) {
      try {
        await updateProduct(editProduct.id, product);
        setEditProduct(null);
        setShowForm(false);
        reload();
      } catch (error: unknown) {
        if (axios.isAxiosError(error) && error.response) {
          alert(error.response.data.message);
        }
      }
    }
  };

  const handleDelete = async (id: number) => {
    if (confirm("Are you sure?")) {
      await deleteProduct(id);
      reload();
    }
  };

  const handleEdit = (product: Product) => {
    setEditProduct(product);
    setShowForm(true);
  };

  const handleCancel = () => {
    setEditProduct(null);
    setShowForm(false);
  };

  const handleToggleAvailable = async (product: Product) => {
    try {
      await updateProduct(product.id, {
        name: product.name,
        description: product.description,
        price: product.price,
        quantity: product.quantity,
        category: product.category,
        available: !product.available
      });
      reload();
    } catch (error: unknown) {
      if (axios.isAxiosError(error) && error.response) {
        alert(error.response.data.message);
      }
    }
  };

  return (
      <div className="app">
        <h1>Product Catalog</h1>
        <SearchBar onSearch={handleSearch} />
        <button onClick={() => setShowForm(true)}>+ New Product</button>
        {showForm && (
            <ProductForm
                onSubmit={editProduct ? handleUpdate : handleCreate}
                onCancel={handleCancel}
                editProduct={editProduct}
            />
        )}
        {loading && <p>Loading...</p>}
        {error && <p className="error-message">{error}</p>}
        {!loading && !error && (
            <ProductList
                products={products}
                onEdit={handleEdit}
                onDelete={handleDelete}
                onToggleAvailable={handleToggleAvailable}
            />
        )}
      </div>
  );
};

export default App;