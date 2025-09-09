import { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { BreadCrumb } from "primereact/breadcrumb";
import { Button } from "primereact/button";

export default function AddBeneficiary() {
    const navigate = useNavigate();

    const breadcrumbItems = [{ label: "Add Beneficiary" }];
    const home = { icon: "pi pi-home", command: () => navigate("/customer") };

    const [formData, setFormData] = useState({
        name: "",
        aadharNumber: "",
        confirmAadharNumber: "",
        ifscCode: "",
        branchName: "",
        description: ""
    });

    const [beneficiaries, setBeneficiaries] = useState([]);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");

    useEffect(() => {
        fetchBeneficiaries();
    }, []);

    const fetchBeneficiaries = async () => {
        try {
            const res = await axios.get("http://localhost:8080/api/beneficiary/get", {
                headers: { Authorization: "Bearer " + localStorage.getItem("token") }
            });
            setBeneficiaries(res.data);
        } catch (err) {
            console.error("Failed to load beneficiaries", err);
        }
    };

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    const validateForm = () => {
        if (!formData.name.trim()) return "Beneficiary name is required";
        if (!formData.aadharNumber.trim()) return "Aadhar number is required";
        if (formData.aadharNumber !== formData.confirmAadharNumber)
            return "Aadhar numbers do not match";
        if (!/^[A-Z]{4}[0-9]{4}$/.test(formData.ifscCode.toUpperCase()))
            return "Invalid IFSC code format (must be 4 letters followed by 4 digits)";
        if (!formData.branchName.trim()) return "Branch name is required";
        return "";
    };

    const handleSubmit = async () => {
        setError("");
        setSuccess("");

        const validationError = validateForm();
        if (validationError) {
            setError(validationError);
            return;
        }

        try {
            const payload = {
                name: formData.name,
                accountNumber: formData.aadharNumber, // backend still expects accountNumber
                ifscCode: formData.ifscCode,
                branchName: formData.branchName,
                description: formData.description
            };

            await axios.post("http://localhost:8080/api/beneficiary/post", payload, {
                headers: { Authorization: "Bearer " + localStorage.getItem("token") }
            });

            setSuccess("Beneficiary added successfully!");
            setFormData({
                name: "",
                aadharNumber: "",
                confirmAadharNumber: "",
                ifscCode: "",
                branchName: "",
                description: ""
            });

            fetchBeneficiaries(); // refresh left list
        } catch (err) {
            setError("Failed to add beneficiary. Try again.");
            console.error(err.response?.data || err.message);
        }
    };

    return (
        <div className="container mt-3">
            <BreadCrumb model={breadcrumbItems} home={home} />

            <div className="row mt-2">
                {/* Left column */}
                <div className="col-md-4">
                    <div className="card shadow p-3">
                        <h5 className="fw-bold mb-3">Your Beneficiaries</h5>
                        <ul className="list-group">
                            {beneficiaries.length > 0 ? (
                                beneficiaries.map((b, idx) => (
                                    <li key={idx} className="list-group-item">
                                        {b.name} <br />
                                    </li>
                                ))
                            ) : (
                                <li className="list-group-item text-muted">
                                    No beneficiaries yet
                                </li>
                            )}
                        </ul>
                    </div>
                </div>

                {/* Right column */}
                <div className="col-md-8">
                    <div className="card p-4 shadow">
                        <h4 className="fw-bold mb-3">Add Beneficiary</h4>

                        {error && <div className="alert alert-danger">{error}</div>}
                        {success && <div className="alert alert-success">{success}</div>}

                        <div className="p-fluid">
                            <div className="mb-3">
                                <label>Beneficiary Name</label>
                                <input
                                    type="text"
                                    name="name"
                                    className="form-control"
                                    value={formData.name}
                                    onChange={handleChange}
                                    required
                                />
                            </div>

                            <div className="mb-3">
                                <label>Aadhar Number</label>
                                <input
                                    type="text"
                                    name="aadharNumber"
                                    className="form-control"
                                    value={formData.aadharNumber}
                                    onChange={handleChange}
                                    required
                                />
                            </div>

                            <div className="mb-3">
                                <label>Re-enter Aadhar Number</label>
                                <input
                                    type="text"
                                    name="confirmAadharNumber"
                                    className="form-control"
                                    value={formData.confirmAadharNumber}
                                    onChange={handleChange}
                                    required
                                />
                            </div>

                            <div className="mb-3">
                                <label>IFSC Code</label>
                                <input
                                    type="text"
                                    name="ifscCode"
                                    className="form-control text-uppercase"
                                    value={formData.ifscCode}
                                    onChange={handleChange}
                                    required
                                />
                            </div>

                            <div className="mb-3">
                                <label>Branch Name</label>
                                <input
                                    type="text"
                                    name="branchName"
                                    className="form-control"
                                    value={formData.branchName}
                                    onChange={handleChange}
                                    required
                                />
                            </div>

                            <div className="mb-3">
                                <label>Description / Nickname</label>
                                <textarea
                                    name="description"
                                    className="form-control"
                                    value={formData.description}
                                    onChange={handleChange}
                                />
                            </div>

                            <div className="d-flex justify-content-between">
                                <Button
                                    label="Cancel"
                                    className="p-button-secondary"
                                    onClick={() => navigate("/customer/beneficiary")}
                                />
                                <Button
                                    label="Add Beneficiary"
                                    className="p-button-success"
                                    onClick={handleSubmit}
                                />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}
