const LandingContainer = ({ children }) => {
  return (
    <>
      <div className="flex flex-col w-full h-full justify-center items-center bg-black bg-opacity-10 bg-intro-1 bg-center bg-cover">
        {children}
      </div>
    </>
  );
};

export default LandingContainer;
