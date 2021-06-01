import React from 'react'
import { render, unmountComponentAtNode } from "react-dom";
import { act } from 'react-dom/test-utils'

import Index from '../../../main/js/pages/Index'
import { MemoryRouter } from "react-router-dom";
import Board from "../../../main/js/pages/Board";
import Intro from "../../../main/js/pages/Intro";

let container = null;

beforeEach(() => {
    container = document.createElement("div");
    document.body.appendChild(container);
});

afterEach(() => {
    unmountComponentAtNode(container);
    container.remove();
    container = null;
});

it("page rendering test", async () => {
    act(() => {
        render(
            <MemoryRouter>
                <Index />
            </MemoryRouter>,
            container
        );
    });
    expect(container.textContent).toContain("Welcome");

    act(() => {
        render(
            <MemoryRouter>
                <Board />
            </MemoryRouter>,
            container
        );
    });
    expect(container.textContent).toContain("게시판");

    act(() => {
        render(
            <MemoryRouter>
                <Intro />
            </MemoryRouter>,
            container
        );
    });
    expect(container.textContent).toContain("소개");
});