import React from "react";

class Persons extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      list: [],
    };
  }

  componentDidMount() {
    const { access_token } = this.props;
    const headers = new Headers();
    headers.append("Authorization", "Bearer " + access_token);
    fetch("http://localhost:8181/persons", {
      method: "GET",
      accept: "*/*",
      headers: headers,
    })
      .then((d) => d.json())
      .then((d) => this.setState({ list: d }))
      .catch((e) => console.error(e));
  }

  render() {
    const { list } = this.state;
    if (list.lenght === 0) return null;
    return (
      <div>
        <h3>Persons table</h3>
        <table border="1">
          <thead>
            <tr>
              <th>Id</th>
              <th>Name</th>
              <th>Age</th>
            </tr>
          </thead>
          <tbody>
            {list.map((person) => (
              <tr>
                <td>{person.id}</td>
                <td>{person.name}</td>
                <td>{person.age}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    );
  }
}

export default Persons;
