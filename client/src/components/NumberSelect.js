import styled from 'styled-components';
import { useRecoilState } from 'recoil';
import { feNumberState, beNumberState } from '../atom/atom';
import { HiPlus, HiMinus } from 'react-icons/hi';

const Container = styled.div`
  color: var(--grey-dark);
  min-width: max-content;
  height: 40px;

  display: flex;
  justify-content: space-around;

  .fe-number-box {
    border: 1px solid var(--purple-medium);
    border-radius: 8px;
    width: 40%;
    min-width: max-content;
    padding: 10px;
    margin-right: 10px;

    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .be-number-box {
    border: 1px solid var(--purple-medium);
    border-radius: 8px;
    width: 40%;
    min-width: 163px;
    padding: 10px;

    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .number-select {
    display: flex;
    width: max-content;
    align-items: center;
  }

  button {
    border: 2px solid var(--purple);
    border-radius: 100%;
    background-color: white;
    color: var(--purple);
    width: 20px;
    height: 20px;
  }

  .fe-number {
    margin: 0 5px;
  }

  .be-number {
    margin: 0 5px;
  }

  .name {
    min-width: fit-content;
    text-align: center;
    margin-right: 10px;
  }
`;

function NumberSelect() {
  const [feNumber, setFeNumber] = useRecoilState(feNumberState);
  const [beNumber, setBeNumber] = useRecoilState(beNumberState);

  const onFeMinusClick = () => {
    if (feNumber > 0) {
      setFeNumber(feNumber - 1);
    }
  };

  const onFePlusClick = () => {
    if (feNumber >= 0) {
      setFeNumber(feNumber + 1);
    }
  };

  const onBeMinusClick = () => {
    if (beNumber > 0) {
      setBeNumber(beNumber - 1);
    }
  };

  const onBePlusClick = () => {
    if (beNumber >= 0) {
      setBeNumber(beNumber + 1);
    }
  };

  return (
    <Container>
      <div className="fe-number-box">
        <div className="name">프론트엔드</div>
        <div className="number-select">
          <button onClick={onFeMinusClick}>
            <HiMinus />
          </button>
          <div className="fe-number">{feNumber}</div>
          <button onClick={onFePlusClick}>
            <HiPlus />
          </button>
        </div>
      </div>
      <div className="be-number-box">
        <div className="name">백엔드</div>
        <div className="number-select">
          <button onClick={onBeMinusClick}>
            <HiMinus />
          </button>
          <div className="be-number">{beNumber}</div>
          <button onClick={onBePlusClick}>
            <HiPlus />
          </button>
        </div>
      </div>
    </Container>
  );
}

export default NumberSelect;
