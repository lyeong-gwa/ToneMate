const Content = ({ children }) => {
  return (
    <>
      <div className="flex flex-col grow justify-evenly overflow-x-hidden items-center w-full h-screen bg-black">
        {children}
      </div>
    </>
  );
};

export default Content;
