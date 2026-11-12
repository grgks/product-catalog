export enum Category {
    ELECTRONICS = "ELECTRONICS",
    HOME = "HOME",
    CLOTHING = "CLOTHING",
    OTHER = "OTHER"
}

export interface Product {
    id: number;
    name: string;
    description: string;
    price: number;
    quantity: number;
    category: Category;
    sku: string;
    available: boolean;
    createdAt: string;
}

export interface ProductRequest {
    name: string;
    description: string;
    price: number | string;
    quantity: number | string;
    category: Category;
    available: boolean;
}