import axios from "axios";
import {type Product, type ProductRequest, Category } from "./types";

const API = axios.create({
    baseURL: "http://localhost:8080/api/products"
});

export const getAllProducts = () =>
    API.get<Product[]>("");

export const getProductById = (id: number) =>
    API.get<Product>(`/${id}`);

export const searchProducts = (search?: string, category?: Category, available?: boolean) => {
    const params: Record<string, string> = {};
    if (search) params.search = search;
    if (category) params.category = category;
    if (available !== undefined) params.available = String(available);
    return API.get<Product[]>("", { params });
};

export const createProduct = (product: ProductRequest) =>
    API.post<Product>("", product);

export const updateProduct = (id: number, product: ProductRequest) =>
    API.put<Product>(`/${id}`, product);

export const deleteProduct = (id: number) =>
    API.delete(`/${id}`);