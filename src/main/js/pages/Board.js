import React from "react";
import {Link, useLocation} from "react-router-dom";
import axios from "axios";

axios.defaults.baseURL = "http://localhost:8080";
export default class Board extends React.Component {
    constructor() {
        super();

        this.state = {
            currentPage: -1,
            maxPage: -1,
            pageData: [],
        }
    }

    handleClick() {
        const location = window.location.href;
        let page = location.split("/").pop();
        if (page === "board")
            page = 1;

        const queryPage = async (i) => {
            try {
                return (await axios.get("/api/v1/posts/page/" + i)).data;
            } catch (e) {
                return [];
            }
        }

        this.queryPage(page)
            .then((response) => {
                this.setState = {
                    pageData: response.pageItems,
                    maxPage: response.maxPage,
                    currentPage: page,
                };
                console.log(response);
            }).catch((err) => {
            console.log(err);
        });
    }


    componentDidMount() {
        this.handleClick();
    }

    render() {
        return (
            <div className="board">
                <div className="container-md overflow-hidden">
                    <div>
                        <h1 className="text-center display-5 m-5">게시판</h1>
                        <Link className="btn btn-primary mb-2" to="/write">글쓰기</Link>
                    </div>
                    <Table pageData={this.state.pageData}/>
                    <div>
                        <Pagination
                            currentPage={this.state.currentPage}
                            maxPage={this.state.maxPage}
                            onClick={this.handleClick}
                        />
                    </div>
                </div>
            </div>
        )
    }
}

function Pagination(props) {
    const drawLink = (obj) => {
        return (
            <li id={obj} className="page-item">
                <Link className="page-link" to={"/board/" + obj} onClick={props.onClick}>{obj}</Link>
            </li>
        )
    };

    const drawLeftArrow = (target) => {
        if (target) {
            return (
                <li id="left" className="page-item">
                    <Link className="page-link" to={"/board/" + target}>&laquo;</Link>
                </li>
            );
        }
        return (
            <li id="left" className="page-item disabled">
                <Link className="page-link" to="#">&laquo;</Link>
            </li>
        );
    };

    const drawRightArrow = (target) => {
        if (target) {
            return (
                <li id="right" className="page-item">
                    <Link className="page-link" to={"/board/" + target}>&raquo;</Link>
                </li>
            );
        }
        return (
            <li id="right" className="page-item disabled">
                <Link className="page-link" to="#">&raquo;</Link>
            </li>
        );
    };

    let start = props.currentPage - 2 <= 0 ? 1 : props.currentPage - 2;
    let limit = start + 5;

    const result = [];
    result.push(drawLeftArrow(start - 5 <= 0 ? 1 : start - 5));
    for (let i = start; i < limit; i++)
        result.push(<li className="page-item">{drawLink(i)}</li>);
    result.push(drawRightArrow(limit + 5 > props.maxPage ? props.maxPage : limit + 5));

    return (
        <ul className="pagination">
            {result}
        </ul>
    );
}

class Table extends React.Component {
    constructor(props) {
        super(props);
    }

    async queryPage(i) {
        // axios.defaults.baseURL = "http://localhost:8080";
        // let result = null;
        // axios.get("/api/v1/posts/page/" + props.page)
        //     .then(function (response) {
        //         console.log(response.data);
        //         result =  response.data;
        //     });
        //
        // return result;
        try {
            return (await axios.get("/api/v1/posts/page/" + i)).data.pageItems;
        } catch (e) {
            return [];
        }
    }

    drawTableBody() {
        const pageData = this.props.pageData;
        const result = [];
        const drawTr = (data) => {
            return (
                <tr key={data.id}>
                    <td className="text-center">{data.id}</td>
                    <td>{data.title}</td>
                    <td className="text-center">{data.author}</td>
                    <td className="text-center">{data.createdDate}</td>
                    <td className="text-center">{data.modifiedDate}</td>
                </tr>
            );
        }

        for (const data of pageData) {
            result.push(drawTr(data));
        }

        return result;
    }

    render() {
        return (
            <div className="table-responsive mb-3">
                <table className="table table-bordered table-hover table-striped">
                    <thead>
                    <tr>
                        <th className="text-center" style={{width: "7rem"}}>카테고리</th>
                        <th className="text-center">제목</th>
                        <th className="text-center" style={{width: "10rem"}}>글쓴이</th>
                        <th className="text-center" style={{width: "10rem"}}>작성일</th>
                        <th className="text-center" style={{width: "10rem"}}>수정일</th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.drawTableBody()}
                    </tbody>
                </table>
            </div>
        );
    }
}