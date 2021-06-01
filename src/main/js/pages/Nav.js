import React from 'react';
import {
    Link,
    useLocation,
} from "react-router-dom";


export default function Nav() {

    let location = useLocation().pathname.split("/")[1];

    const isActive = (location, names) => {
        for(let name of names) {
            if(location === name)
                return " active";
        }
        return "";
    }

    return (
        <nav className="navbar navbar-expand-md navbar-dark bg-dark">
            <div className="container">
                <Link className="navbar-brand" to="/">Home</Link>
                <button className="navbar-toggler" type="button" data-bs-toggle="collapse"
                        data-bs-target="#collapsibleNavbar">
                    <span className="navbar-toggler-icon"/>
                </button>
                <div className="collapse navbar-collapse" id="collapsibleNavbar">
                    <ul className="navbar-nav ms-auto">
                        <li className="nav-item">
                            <Link className={"nav-link" + isActive(location, ["intro"])} to="/intro">소개</Link>
                        </li>
                        <li className="nav-item">
                            <Link className={"nav-link" + isActive(location, ["board", "write"])} to="/board">게시판</Link>
                        </li>
                        <li className="nav-item">
                            <a className="nav-link" href="#">문의</a>
                        </li>

                        <li className="nav-item">
                            <a className="nav-link" href="#">Login</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    )
}