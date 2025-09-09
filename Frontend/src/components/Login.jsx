import { useState } from "react"
import axios from "axios"
import { Link, useNavigate } from "react-router-dom"
import { Eye, EyeSlash } from 'react-bootstrap-icons'
import './../css/login.css'
import { setUserDetails } from "../store/actions/UserAction"
import { useDispatch } from "react-redux"


function Login() {

    const navigate = useNavigate()
    const dispatch = useDispatch()

    let [username, setUsername] = useState("")
    let [password, setPassword] = useState("")
    let [msg, setMsg] = useState("")
    const [showPassword, setShowPassword] = useState(false);

    const processLogin = async () => {
        let encodedString = window.btoa(username+":"+password)
        try{
            const response = await axios.get("http://localhost:8080/api/user/token",{
                headers : { "Authorization":"Basic "+encodedString}
            })
            // console.log(response.data.token)
            let token = response.data.token
            localStorage.setItem("token", token)

            let details = await axios.get("http://localhost:8080/api/user/details",{
                headers: {"Authorization":"Bearer "+token}
            })
            // console.log(details)
            let user = {
                'username': username,
                'role': details.data.user.role
            }
            localStorage.setItem("user", JSON.stringify(user));
            setUserDetails(dispatch)(user)


            let role = details.data.user.role
            switch (role) {
                case "CUSTOMER":
                    navigate("/customer")
                    break;
                case "EXECUTIVE":
                    localStorage.setItem("branchId", details.data.branch.id)
                    navigate("/executive")
                    break;
                case "MANAGER":
                    navigate("/manager")
                    break;
                case "ADMIN":
                    navigate("/admin")
                    break;
                default:
                    setMsg("Login Disabled!.. Contact admin at admin@example.com")
                    break;
            }
            setMsg("Login Success!!")
        }catch(err){
            setMsg("Invalid Credentials!!")
            if(err.response.data.msg == "INACTIVE")
                setMsg("Account is deactivated, please contact admin at admin@mavk.com")
        }
    }

    return (
        <div className="login-container-fluid min-vh-100 d-flex">
            <div className="row w-100 m-0">

                {/* Left Column - Branding */}
                <div className="col-md-6 d-flex flex-column justify-content-center align-items-center text-center p-5 login-left">
                    <img src="/images/logo-transparent.png" className="login-logo mb-4" alt="Maverick Bank Logo" />
                    <h1 className="display-4 fw-bold text-white mb-3">Maverick Bank</h1>
                    <p className="lead text-white login-text-shadow-md w-75">
                        Secure, reliable, and accessible banking at your fingertips.
                        Your trusted partner for savings, investments, and growth.
                    </p>
                </div>

                {/* Right Column - Login */}
                <div className="col-md-6 d-flex justify-content-center align-items-center p-5 login-right">
                    <div className="card shadow-lg border-0 rounded-4 w-100" style={{ maxWidth: "420px" }}>
                        <div className="card-body p-4">

                            {/* Title */}
                            <div className="text-center mb-4">
                                <h3 className="login-card-title login-gradient-text">Sign In</h3>
                                <p className="text-muted small">Access your account securely</p>
                            </div>

                            {msg !== "" && (
                                <div className="alert alert-info text-center py-2">{msg}</div>
                            )}

                            <form>
                                <div className="form-floating mb-3">
                                    <input
                                        type="email"
                                        className="form-control rounded-3"
                                        placeholder="name@example.com"
                                        onChange={(e) => setUsername(e.target.value)}
                                        autoComplete="username"
                                    />
                                    <label>Email Address</label>
                                </div>

                                <div className="form-floating position-relative mb-3">
                                    <input
                                        type={showPassword ? "text" : "password"}
                                        className="form-control rounded-3"
                                        placeholder="Password"
                                        onChange={(e) => setPassword(e.target.value)}
                                        autoComplete="current-password"
                                    />
                                    <label>Password</label>
                                    <span
                                        onClick={() => setShowPassword(!showPassword)}
                                        style={{
                                            position: "absolute",
                                            right: "15px",
                                            top: "50%",
                                            transform: "translateY(-50%)",
                                            cursor: "pointer",
                                        }}
                                    >
                  {showPassword ? <EyeSlash /> : <Eye />}
                </span>
                                </div>
                            </form>

                            <div className="d-grid mt-4">
                                <button
                                    className="login-btn btn rounded-3 py-2 fw-semibold"
                                    onClick={() => processLogin()}
                                >
                                    Log In
                                </button>
                            </div>

                            <div className="text-center mt-3">
                                <small>
                                    Don’t have an account?{" "}
                                    <Link
                                        onClick={() => navigate("/signup")}
                                        to="/signup"
                                        className="fw-bold text-decoration-none"
                                    >
                                        Sign Up
                                    </Link>
                                </small>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )

}

export default Login