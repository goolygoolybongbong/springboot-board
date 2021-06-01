import React from 'react';
import Nav from './Nav';
import {Link} from "react-router-dom";

export default function Write() {
    const submit = () => {
        // TODO : 제출 구현
    }

    return (
        <div className="write">
            <div className="container-md overflow-hidden">
                <div>
                    <h1 className="text-center display-5 m-5">글 작성하기</h1>
                </div>
                <form>
                    <div className="mb-3">
                        <label htmlFor="title" className="form-label fw-bold">제목</label>
                        <input type="text" className="form-control" id="title" aria-describedby="title"
                               placeholder="제목을 입력하세요" />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="writer" className="form-label fw-bold">글쓴이</label>
                        <input type="text" className="form-control" id="writer" aria-describedby="writer"
                               placeholder="글쓴이" />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="content" className="form-label fw-bold">내용</label>
                        <textarea className="form-control" id="content" rows="13" placeholder="내용을 입력하세요" />
                    </div>
                    <div className="mb-3 form-check">

                        <label className="form-check-label" htmlFor="private">익명으로 작성하기</label>
                        <input type="checkbox" className="form-check-input" id="private" />
                    </div>
                    <button type="submit" className="btn btn-primary m-1" id="btn-save" onClick={submit}>제출</button>
                    <Link href="/" className="btn btn-secondary m-1">취소</Link>
                </form>
            </div>
        </div>
    )
}