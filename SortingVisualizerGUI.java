import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class SortingVisualizerGUI extends JFrame {
    private JTextField inputField;
    private JTextArea outputArea;

    public SortingVisualizerGUI() {
        super("Sorting Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        JLabel inputLabel = new JLabel("Enter elements (comma-separated): ");
        inputField = new JTextField(30);
        JButton sortButton = new JButton("Sort");
        inputPanel.add(inputLabel);
        inputPanel.add(inputField);
        inputPanel.add(sortButton);

        outputArea = new JTextArea(15, 50);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);

        sortButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = inputField.getText().trim();
                if (input.isEmpty()) {
                    JOptionPane.showMessageDialog(SortingVisualizerGUI.this, "Please enter some elements.");
                    return;
                }

                try {
                    int[] arr = Arrays.stream(input.split(","))
                                      .map(String::trim)
                                      .mapToInt(Integer::parseInt)
                                      .toArray();

                    outputArea.setText("");
                    outputArea.append("Original Array: " + Arrays.toString(arr) + "\n\n");

                    int choice = showSortingOptions();

                    switch (choice) {
                        case 1:
                            selectionSort(arr);
                            break;
                        case 2:
                            insertionSort(arr);
                            break;
                        case 3:
                            bubbleSort(arr);
                            break;
                        case 4:
                            mergeSort(arr);
                            break;
                        case 5:
                            quickSort(arr);
                            break;
                        case 6:
                            heapSort(arr);
                            break;
                        default:
                            outputArea.append("Invalid choice.\n");
                            break;
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SortingVisualizerGUI.this, "Invalid input format. Please enter integers separated by commas.");
                }
            }
        });
    }

    private int showSortingOptions() {
        String[] options = { "Selection Sort", "Insertion Sort", "Bubble Sort", "Merge Sort", "Quick Sort", "Heap Sort" };
        return JOptionPane.showOptionDialog(this,
                "Choose a sorting algorithm:",
                "Sorting Algorithm",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]) + 1; // Add 1 to match with switch-case in the sorting methods
    }

    private void selectionSort(int[] arr) {
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;

            outputArea.append("After iteration " + (i + 1) + ": " + Arrays.toString(arr) + "\n");
        }

        outputArea.append("Sorted Array: " + Arrays.toString(arr) + "\n");
    }

    private void insertionSort(int[] arr) {
        int n = arr.length;

        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;

            outputArea.append("After iteration " + i + ": " + Arrays.toString(arr) + "\n");
        }

        outputArea.append("Sorted Array: " + Arrays.toString(arr) + "\n");
    }

    private void bubbleSort(int[] arr) {
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }

            outputArea.append("After iteration " + (i + 1) + ": " + Arrays.toString(arr) + "\n");

            if (!swapped) {
                break;
            }
        }

        outputArea.append("Sorted Array: " + Arrays.toString(arr) + "\n");
    }

    private void mergeSort(int[] arr) {
        int[] tempArr = Arrays.copyOf(arr, arr.length);
        mergeSort(tempArr, 0, tempArr.length - 1);
        outputArea.append("Sorted Array: " + Arrays.toString(tempArr) + "\n");
    }

    private void mergeSort(int[] arr, int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;

            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);

            merge(arr, l, m, r);
        }
    }

    private void merge(int[] arr, int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;

        int[] left = new int[n1];
        int[] right = new int[n2];

        for (int i = 0; i < n1; i++) {
            left[i] = arr[l + i];
        }
        for (int j = 0; j < n2; j++) {
            right[j] = arr[m + 1 + j];
        }

        int i = 0, j = 0;
        int k = l;

        while (i < n1 && j < n2) {
            if (left[i] <= right[j]) {
                arr[k] = left[i];
                i++;
            } else {
                arr[k] = right[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = left[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = right[j];
            j++;
            k++;
        }

        outputArea.append("Merging: " + Arrays.toString(arr) + "\n");
    }

    private void quickSort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
        outputArea.append("Sorted Array: " + Arrays.toString(arr) + "\n");
    }

    private void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);

            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;

                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }

    private void heapSort(int[] arr) {
        int n = arr.length;

        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // One by one extract an element from heap
        for (int i = n - 1; i > 0; i--) {
            // Move current root to end
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // call max heapify on the reduced heap
            heapify(arr, i, 0);

            outputArea.append("After iteration " + (n - i) + ": " + Arrays.toString(arr) + "\n");
        }

        outputArea.append("Sorted Array: " + Arrays.toString(arr) + "\n");
    }

    private void heapify(int[] arr, int n, int i) {
        int largest = i; // Initialize largest as root
        int left = 2 * i + 1; // left = 2*i + 1
        int right = 2 * i + 2; // right = 2*i + 2

        // If left child is larger than root
        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }

        // If right child is larger than largest so far
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }

        // If largest is not root
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                SortingVisualizerGUI gui = new SortingVisualizerGUI();
                gui.setVisible(true);
            }
        });
    }
}
