import { useState } from "react";
import { Category } from "../types";

interface SearchBarProps {
    onSearch: (search: string, category: Category | undefined, available: boolean | undefined) => void;
}

const SearchBar = ({ onSearch }: SearchBarProps) => {
    const [search, setSearch] = useState("");
    const [category, setCategory] = useState<Category | undefined>(undefined);
    const [available, setAvailable] = useState<boolean | undefined>(undefined);

    const handleSearch = () => {
        onSearch(search, category, available);
    };

    const handleReset = () => {
        setSearch("");
        setCategory(undefined);
        setAvailable(undefined);
        onSearch("", undefined, undefined);
    };

    return (
        <div className="search-bar">
            <input
                type="text"
                placeholder="Search by name..."
                value={search}
                onChange={(e) => setSearch(e.target.value)}
            />
            <select
                value={category ?? ""}
                onChange={(e) => setCategory(e.target.value ? e.target.value as Category : undefined)}
            >
                <option value="">All Categories</option>
                {Object.values(Category).map((cat) => (
                    <option key={cat} value={cat}>{cat}</option>
                ))}
            </select>
            <select
                value={available === undefined ? "" : String(available)}
                onChange={(e) => setAvailable(e.target.value === "" ? undefined : e.target.value === "true")}
            >
                <option value="">All</option>
                <option value="true">Available</option>
                <option value="false">Unavailable</option>
            </select>
            <button onClick={handleSearch}>Search</button>
            <button onClick={handleReset}>Reset</button>
        </div>
    );
};

export default SearchBar;