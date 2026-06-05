import"./SideBar.css";

export const SideBar = ({children, titulo}) => {
    return (
        <nav className="barra-lat">
            <h1>{titulo}</h1>
            <div>
                {children}
            </div>
        </nav>
    )
}