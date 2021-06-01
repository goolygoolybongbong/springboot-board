import React from "react";
import ReactDOM from "react-dom";

import {
    BrowserRouter as Router,
    Switch,
    Route,
} from "react-router-dom";

import Index from "./pages/Index";
import Intro from "./pages/Intro";
import Board from "./pages/Board";
import Write from "./pages/Write";
import Nav from "./pages/Nav";
import Posts from "./pages/Posts";

// class App extends React.Component {
//     constructor(props) {
//         super(props);
//     }
//
//     render() {
//         return (
//             <div className="container">
//                 <h1 className="text-center display-5 m-5">Index</h1>
//             </div>
//         );
//     }
// }

class Routes extends React.Component {
    render() {
        return (
            <Router>
                <Nav />
                <Switch >
                    <Route exact path="/" component={Index} />
                    <Route exact path="/board" component={Board} />
                    <Route exact path="/intro" component={Intro} />
                    <Route exact path="/write" component={Write} />
                    <Route exact path="/posts" component={Posts} />
                </Switch>
            </Router>
        );
    }
}

ReactDOM.render(
    <Routes />,
    document.getElementById("react")
);