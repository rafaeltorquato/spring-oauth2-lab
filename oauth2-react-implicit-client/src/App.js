import "./App.css";
import React from "react";
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      access_token: "",
      expires_in: "",
      session_state: "",
      token_type: "",
    };
  }

  setSessionState = (key, value) => {
    if (this.state[key] !== value) {
      this.setState({ [key]: value });
    }
  };

  componentDidMount() {
    const url = document.location.toString();
    const params = new URLSearchParams(document.location.hash);
    if (url.includes("/post-login") && params.get("access_token")) {
      this.setState({ access_token: params.get("access_token") });
      this.setState({ expires_in: params.get("expires_in") });
      this.setState({ session_state: params.get("session_state") });
      this.setState({ token_type: params.get("token_type") });
    }
  }

  render() {
    return (
      <Router>
        <div>
          <nav>
            <ul>
              <li>
                <Link to="/">Home</Link>
              </li>
              <li>
                <Link to="/login">Login</Link>
              </li>
              <li>
                <Link to="/books">Books</Link>
              </li>
              <li>
                <Link to="/persons">Persons</Link>
              </li>
            </ul>
          </nav>

          {/* A <Switch> looks through its children <Route>s and
              renders the first one that matches the current URL. */}
          <Switch>
            <Route path="/login">
              <Login />
            </Route>
            <Route path="/books">
              <Books access_token={this.state.access_token} />
            </Route>
            <Route path="/persons">
              <Persons access_token={this.state.access_token} />
            </Route>
            <Route path="/">
              <Home />
            </Route>
          </Switch>
        </div>
      </Router>
    );
  }
}

function Home() {
  return <h2>Home</h2>;
}

function Login() {
  window.location =
    "http://localhost:8888/auth/realms/oauth2-study/protocol/openid-connect/auth?client_id=oauth2-client-implicit&response_type=token&redirect_uri=http://localhost:3000/post-login";
  return null;
}

function Books(props) {
  return <h2>Books</h2>;
}

function Persons(props) {
  const { access_token } = props;
  const headers = new Headers();
  headers.append("Authorization", "Bearer " + access_token);
  fetch("http://localhost:8181/persons", {
    method: "GET",
    accept: "*/*",
    headers: headers,
  })
    .then((d) => d.json())
    .then((d) => console.log(d))
    .catch((e) => console.error(e));
  return <h2>Persons</h2>;
}

export default App;
