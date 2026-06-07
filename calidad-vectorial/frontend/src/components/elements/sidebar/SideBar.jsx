import"./SideBar.css";

export const SideBar = ({children, titulo}) => {
    return (
        <nav className="sidebar">
            <div className="sidebar__content">
                <div>
                    <h1>{titulo}</h1>
                    <div className="sidebar__cards">
                        {children}
                    </div>
                </div>
                <div className="sidebar__content__footer">
                    <p>Bücher</p>
                </div>
            </div>
        </nav>
    )
}