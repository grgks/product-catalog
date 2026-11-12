import { useState } from "react";
import { Category, type Product, type ProductRequest } from "../types";

interface ProductFormProps {
    onSubmit: (product: ProductRequest) => void;
    onCancel: () => void;
    editProduct?: Product | null;
}

const ProductForm = ({ onSubmit, onCancel, editProduct }: ProductFormProps) => {
    const initialForm: ProductRequest = editProduct
        ? {
            name: editProduct.name,
            description: editProduct.description,
            price: editProduct.price,
            quantity: editProduct.quantity,
            category: editProduct.category,
            available: editProduct.available
        }
        : {
            name: "",
            description: "",
            price: "" ,
            quantity: "",
            category: Category.OTHER,
            available: false
        };

    const [form, setForm] = useState<ProductRequest>(initialForm);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>) => {
        const { name, value, type } = e.target;
        setForm(prev => ({
            ...prev,
            [name]: type === "checkbox" ? (e.target as HTMLInputElement).checked
                : type === "number" ? (value === "" ? "" : Number(value))
                    : value
        }));
    };

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        onSubmit(form);
    };

    return (
        <form onSubmit={handleSubmit} className="product-form">
            <h3>{editProduct ? "Edit Product" : "New Product"}</h3>
            <label>Name
                <input name="name" value={form.name} onChange={handleChange} required />
            </label>
            <label>Description
                <textarea name="description" value={form.description} onChange={handleChange} required />
            </label>
            <label>Price
                <input name="price" type="number" step="0.01" min="0" placeholder="0.00" value={form.price} onChange={handleChange} required />
            </label>
            <label>Quantity
                <input name="quantity" type="number" min="0" placeholder="0.00" value={form.quantity} onChange={handleChange} required />
            </label>
            <label>Category
                <select name="category" value={form.category} onChange={handleChange}>
                    {Object.values(Category).map((cat) => (
                        <option key={cat} value={cat}>{cat}</option>
                    ))}
                </select>
            </label>
            <label>
                <input name="available" type="checkbox" checked={form.available} onChange={handleChange} />
                Available
            </label>
            <div>
                <button type="submit">{editProduct ? "Update" : "Create"}</button>
                <button type="button" onClick={onCancel}>Cancel</button>
            </div>
        </form>
    );
};

export default ProductForm;