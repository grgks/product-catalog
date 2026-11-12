import { type Product } from "../types";

interface ProductListProps {
    products: Product[];
    onEdit: (product: Product) => void;
    onDelete: (id: number) => void;
    onToggleAvailable: (product: Product) => void;
}

const ProductList = ({ products, onEdit, onDelete, onToggleAvailable }: ProductListProps) => {
    if (products.length === 0) {
        return <p>No products found.</p>;
    }

    return (
        <table className="product-table">
            <thead>
            <tr>
                <th>SKU</th>
                <th>Name</th>
                <th>Category</th>
                <th>Price</th>
                <th>Qty</th>
                <th>Available</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            {products.map((product) => (
                <tr key={product.id}>
                    <td>{product.sku}</td>
                    <td>{product.name}</td>
                    <td>{product.category}</td>
                    <td>{product.price.toFixed(2)}€</td>
                    <td>{product.quantity}</td>
                    <td>
                        <button onClick={() => onToggleAvailable(product)}>
                            {product.available ? "✅" : "❌"}
                        </button>
                    </td>
                    <td>
                        <button onClick={() => onEdit(product)}>Edit</button>
                        <button onClick={() => onDelete(product.id)}>Delete</button>
                    </td>
                </tr>
            ))}
            </tbody>
        </table>
    );
};

export default ProductList;