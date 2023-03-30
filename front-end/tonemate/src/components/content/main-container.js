const MainContainer = ({ children }) => {
  return (
    <>
      <div className="flex flex-col w-11/12 h-5/6 justify-between items-center bg-black bg-opacity-10">
        {children}
      </div>
    </>
  );
};

export default MainContainer;
