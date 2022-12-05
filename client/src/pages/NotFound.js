import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import notFoundPage from '../assets/image/not.png';

const NotFoundContainer = styled.div`
  img {
    width: 80%;
    margin-bottom: 20px;
    height: 100%;
  }
  .not-found-wrapper {
  }
  button {
  }
  .not-found-btn {
  }
`;

const NotFound = () => {
  const navigate = useNavigate();
  return (
    <NotFoundContainer>
      <div className="not-found-wrapper">
        {/* <img src={notFound} alt="error-img"></img> */}
        <img src={notFoundPage} alt="error-img"></img>
        <div className="not-found-btn">
          <button
            className="go-home-btn"
            onClick={() => {
              navigate(`/`);
            }}
          >
            back to home
          </button>
        </div>
      </div>
    </NotFoundContainer>
  );
};

export default NotFound;
