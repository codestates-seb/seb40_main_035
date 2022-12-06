import { useEffect, useState } from 'react';
import { IoIosArrowUp } from 'react-icons/io';
import styled from 'styled-components';

const Button = styled.button`
  position: fixed;
  bottom: 100px;
  right: 100px;
  width: 60px;
  height: 60px;
  background-color: #ffffff;
  border-radius: 50%;
  border: none;
  box-shadow: rgba(149, 157, 165, 0.2) 0px 8px 24px;
  animation: fadein 1s alternate;

  @keyframes fadein {
    from {
      opacity: 0;
      transform: translate(0, 100px);
    }
    to {
      opacity: 1;
      transform: translate(0, 0);
    }
  }
`;

const ScrollTopButton = () => {
  const [showButton, setShowButton] = useState(false);
  const scrollToTop = () => {
    window.scrollTo({
      top: 0,
      behavior: 'smooth',
    });
  };
  useEffect(() => {
    const ShowButtonClick = () => {
      setShowButton(window.scrollY > 100);
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

export default ScrollTopButton;
