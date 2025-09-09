import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { setUserDetails } from "../../store/actions/UserAction";
import Logo from '/images/logo-home-transparent.png';

function AdminNavbar() {
    const user = useSelector(state => state.user);
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const logout = () => {
        let u = { username: "", role: "" };
        setUserDetails(dispatch)(u);
        localStorage.clear();
        navigate("/");
    };

    return (
        <nav className="customer-navbar">
            {/* Left: Logo */}
            <div className="navbar-left">
                <img
                    src={Logo}
                    alt="Maverick Bank"
                    className="nav-logo"
                />
            </div>

            {/* Right: Profile + User */}
            <div className="navbar-right">
                <span className="welcome-text">
                    Welcome, <strong>{user.username}</strong>
                </span>
                <div className="dropdown">
                    <img
                        src="https://cdn-icons-png.flaticon.com/512/149/149071.png"
                        alt="profile"
                        className="profile-icon dropdown-toggle"
                        data-bs-toggle="dropdown"
                    />
                    <ul className="dropdown-menu dropdown-menu-end custom-dropdown">
                        <li>
                            <a className="dropdown-item" href="#profile">
                                Profile Management
                            </a>
                        </li>
                        <li><hr className="dropdown-divider" /></li>
                        <li>
                            <button className="dropdown-item" onClick={logout}>
                                Logout
                            </button>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    );
}

export default AdminNavbar;
