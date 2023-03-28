const Content = ({ children }) => {
  return (
    <>
      <div className="flex flex-col grow justify-evenly items-center h-screen bg-black">
        {children}
      </div>
    </>
  );
};

export default Content;
