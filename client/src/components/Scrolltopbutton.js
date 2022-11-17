import { useEffect, useState } from 'react';
import { IoIosArrowUp } from 'react-icons/io';
import styled from 'styled-components';

const Button = styled.button`
  background-color: #ffffff;
  border-radius: 50%;
  border: none;
`;

const Scrolltopbutton = () => {
  const [showButton, setShowButton] = useState(false);
  const scrollToTop = () => {
    window.scrollTo({
      top: 0,
      behavior: 'smooth',
    });
  };
  useEffect(() => {
    const ShowButtonClick = () => {
      if (window.scrollY > 100) {
        setShowButton(true);
      } else {
        setShowButton(false);
      }
    };
    window.addEventListener('scroll', ShowButtonClick);
    return () => {
      window.removeEventListener('scroll', ShowButtonClick);
    };
  }, []);

  return (
    <>
      {showButton && (
        <Button onClick={scrollToTop}>
          <IoIosArrowUp color="775CBB" size="30px" />
        </Button>
      )}
    </>
  );
};

export default Scrolltopbutton;
